package com.bizease.api.app.security.jwt;
import java.io.IOException;
import java.util.List;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.bizease.api.app.model.user.entities.User;
import com.bizease.api.app.model.user.repository.UserRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final JwtService jwtService;
  private final UserRepository userRepository;

  private final List<String> PUBLIC_PATHS = List.of(
      "/auth/sign-in");

  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
    String path = request.getRequestURI();
    return PUBLIC_PATHS.contains(path);
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request,
      HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {

    String authHeader = request.getHeader("Authorization");
    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      filterChain.doFilter(request, response);
      throw new BadCredentialsException("Token ausente ou malformado");
    }

    String token = authHeader.substring(7);
    if (!jwtService.isTokenValid(token)) {
      filterChain.doFilter(request, response);
      throw new BadCredentialsException("Token inválido ou expirado");
    }
    String userId = jwtService.extractUserId(token);
    String tenantId = jwtService.extractTenantId(token);
    User user = userRepository.findById(Long.parseLong(userId)).orElse(null);

    if (user != null) {
      UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user, null, List.of());
      authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
      SecurityContextHolder.getContext().setAuthentication(authToken);
    }

    JwtAuthContext.set(new JwtAuthContext(userId, tenantId));

    try {
      filterChain.doFilter(request, response);
    } finally {
      JwtAuthContext.clear();
    }
  }

}
