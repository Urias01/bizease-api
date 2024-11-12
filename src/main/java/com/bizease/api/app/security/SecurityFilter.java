package com.bizease.api.app.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.bizease.api.app.providers.JWTProvider;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityFilter extends OncePerRequestFilter {

  @Autowired
  private JWTProvider jwtProvider;

  @Override
  protected void doFilterInternal(HttpServletRequest request,
      HttpServletResponse response,
      FilterChain filterChain)
      throws ServletException, IOException {

    String header = request.getHeader("Authorization");

    String requestURI = request.getRequestURI();

    if (requestURI.startsWith("/auth/users")
        || requestURI.startsWith("/users/first-access")
        || requestURI.startsWith("/auth/password-reset")) {
      filterChain.doFilter(request, response); // Permita que a requisição prossiga
      return; // Saia do filtro
    }

    if (header == null || !header.startsWith("Bearer ")) {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      response.getWriter().write("Token JWT ausente ou no formato inválido.");
      return;
    }

    var token = this.jwtProvider.validateToken(header);

    if (token == null) {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      response.getWriter().write("Usuário não está autenticado no sistema ou expirou sua sessão.");
      return;
    }

    try {

      String commerceUuid = token.getClaim("commerce").asString();
      request.setAttribute("commerce_uuid", commerceUuid);

      request.setAttribute("user_uuid", token.getSubject());
      var roles = token.getClaim("roles").asList(Object.class);

      var grants = roles.stream().map(
          role -> new SimpleGrantedAuthority("ROLE_" + role.toString())).toList();

      UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(token.getSubject(), null,
          grants);
      SecurityContextHolder.getContext().setAuthentication(auth);

      filterChain.doFilter(request, response);
    } catch (TokenExpiredException e) {
      // Trata o caso de token expirado e retorna 401
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      response.setContentType("application/json");
      response.getWriter()
          .write("{\"code\": \"TOKEN_EXPIRED\", \"message\": \"Sessão expirada. Faça login novamente.\"}");
    } catch (JWTVerificationException e) {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      if (e.getMessage().contains("expirado")) {
        response.getWriter().write("Sessão expirada. Faça login novamente.");
      } else {
        response.getWriter().write("Token inválido.");
      }
    }

  }
}