package com.bizease.api.app.useCases.users;

import org.springframework.stereotype.Service;

import com.bizease.api.app.repositories.UserRepository;
import com.bizease.api.app.exceptions.NotFoundException;
import com.bizease.api.app.mappers.UserMapper;
import com.bizease.api.app.models.User;
import com.bizease.api.app.models.response.UserResponse;
import com.bizease.api.app.security.jwt.IJwtAuthContext;

@Service
public class GetUserById {

  private final UserRepository userRepository;
  private final IJwtAuthContext jwtAuthContext;

  public GetUserById(UserRepository userRepository, IJwtAuthContext jwtAuthContext) {
    this.userRepository = userRepository;
    this.jwtAuthContext = jwtAuthContext;
  }

  public UserResponse execute(String userId) {
    User user = userRepository.findByIdAndTenantId(userId, jwtAuthContext.getTenantId())
        .orElseThrow(() -> new NotFoundException("User"));

    return UserMapper.toResponse(user);
  }
}