package com.bizease.api.app.services.users;

import org.springframework.stereotype.Service;

import com.bizease.api.app.exceptions.NotFoundException;
import com.bizease.api.app.infrastructure.persistence.jpa.user.UserRepository;
import com.bizease.api.app.infrastructure.security.jwt.IJwtAuthContext;
import com.bizease.api.app.models.entities.User;
import com.bizease.api.app.models.enums.AccessStatus;

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
