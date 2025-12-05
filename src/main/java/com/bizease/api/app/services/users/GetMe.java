package com.bizease.api.app.services.users;

import org.springframework.stereotype.Service;

import com.bizease.api.app.exceptions.NotFoundException;
import com.bizease.api.app.infrastructure.persistence.jpa.user.UserRepository;
import com.bizease.api.app.infrastructure.security.jwt.IJwtAuthContext;
import com.bizease.api.app.mappers.UserMapper;
import com.bizease.api.app.models.entities.User;
import com.bizease.api.app.models.response.UserResponse;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class GetMe {

  private final UserRepository userRepository;
  private final IJwtAuthContext jwtAuthContext;
  
  public UserResponse execute() {
    String userId = jwtAuthContext.getUserId();
    String tenantId = jwtAuthContext.getTenantId();

    User user = userRepository.findByIdAndTenantId(userId, tenantId)
        .orElseThrow(() -> new NotFoundException("User"));
        
    return UserMapper.toResponse(user);
  }
}