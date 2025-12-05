package com.bizease.api.app.services.auth;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bizease.api.app.exceptions.AuthenticationException;
import com.bizease.api.app.exceptions.NotFoundException;
import com.bizease.api.app.infrastructure.persistence.jpa.tenant.TenantRepository;
import com.bizease.api.app.infrastructure.persistence.jpa.user.UserRepository;
import com.bizease.api.app.infrastructure.security.jwt.JwtService;
import com.bizease.api.app.models.entities.Tenant;
import com.bizease.api.app.models.entities.User;
import com.bizease.api.app.models.enums.AccessStatus;
import com.bizease.api.app.models.request.AuthRequest;
import com.bizease.api.app.models.response.AuthResponse;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SignIn {

  private final UserRepository userRepository;
  private final TenantRepository tenantRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;

  public AuthResponse execute(AuthRequest request) {

    Tenant tenant = tenantRepository.findBySlug(request.slug())
        .orElseThrow(() -> new NotFoundException("Invalid email or password"));

    User user = userRepository.findByEmailAndTenantId(request.email(), tenant.getId())
        .orElseThrow(() -> new NotFoundException("Invalid email or password"));

    if (!passwordEncoder.matches(request.password(), user.getPassword())) {
      throw new AuthenticationException("Invalid email or password");
    }

    if (user.getStatus().equals(AccessStatus.INACTIVE)) {
      throw new AuthenticationException("User is inactive");
    }

    String token = jwtService.generateToken(user.getId().toString(), tenant.getId());

    return new AuthResponse(token);
  }
}
