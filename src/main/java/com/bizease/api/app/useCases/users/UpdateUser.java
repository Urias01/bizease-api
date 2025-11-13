package com.bizease.api.app.useCases.users;

import org.springframework.stereotype.Service;

import com.bizease.api.app.repositories.UserRepository;
import com.bizease.api.app.exceptions.NotFoundException;
import com.bizease.api.app.models.User;
import com.bizease.api.app.models.request.UpdateUserRequest;
import com.bizease.api.app.security.jwt.IJwtAuthContext;

@Service
public class UpdateUser {

  private final UserRepository userRepository;
  private final IJwtAuthContext jwtAuthContext;

  public UpdateUser(UserRepository userRepository, IJwtAuthContext jwtAuthContext) {
    this.userRepository = userRepository;
    this.jwtAuthContext = jwtAuthContext;
  }

  public String execute(String userId, UpdateUserRequest request) {
    User user = userRepository.findByIdAndTenantId(userId, jwtAuthContext.getTenantId())
        .orElseThrow(() -> new NotFoundException("User"));

    user.setName(request.name());
    user.setEmail(request.email());

    userRepository.save(user);
    return user.getId();
  }
}
