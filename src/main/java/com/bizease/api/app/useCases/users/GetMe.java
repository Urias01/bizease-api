package com.bizease.api.app.useCases.users;

import org.springframework.stereotype.Service;

import com.bizease.api.app.exceptions.NotFoundException;
import com.bizease.api.app.mappers.UserMapper;
import com.bizease.api.app.models.User;
import com.bizease.api.app.models.response.UserResponse;
import com.bizease.api.app.repositories.UserRepository;
import com.bizease.api.app.security.jwt.IJwtAuthContext;

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