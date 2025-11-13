package com.bizease.api.app.useCases.users;

import org.springframework.stereotype.Service;

import com.bizease.api.app.exceptions.NotFoundException;
import com.bizease.api.app.models.User;
import com.bizease.api.app.models.enums.AccessStatus;
import com.bizease.api.app.repositories.UserRepository;
import com.bizease.api.app.security.jwt.IJwtAuthContext;

@Service
public class DeleteUser {

  private final UserRepository userRepository;
  private final IJwtAuthContext jwtAuthContext;

  public DeleteUser(UserRepository userRepository, IJwtAuthContext jwtAuthContext) {
    this.userRepository = userRepository;
    this.jwtAuthContext = jwtAuthContext;
  }

  public void execute(String userId) {
    User user = userRepository.findByIdAndTenantId(userId, jwtAuthContext.getTenantId())
        .orElseThrow(() -> new NotFoundException("User"));

    user.setStatus(AccessStatus.INACTIVE);

    userRepository.save(user);
  }
}
