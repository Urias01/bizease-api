package com.bizease.api.app.model.user.useCases;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bizease.api.app.exceptions.NotFoundException;
import com.bizease.api.app.exceptions.UnauthorizedAccessException;
import com.bizease.api.app.model.commons.enums.IsActiveEnum;
import com.bizease.api.app.model.user.dto.UpdateUserRequestDTO;
import com.bizease.api.app.model.user.entities.User;
import com.bizease.api.app.model.user.enums.RoleEnum;
import com.bizease.api.app.model.user.repository.UserRepository;

@Service
public class ManagerUsersUseCase {

  @Autowired
  private UserRepository userRepository;
  @Autowired
  private PasswordEncoder passwordEncoder;

  public Long execute(String uuid, String role, UpdateUserRequestDTO userToUpdate) {

    if (!RoleEnum.fromString(role).equals(RoleEnum.OWNER) && !RoleEnum.fromString(role).equals(RoleEnum.ADMIN)) {
      throw new UnauthorizedAccessException();
    }

    Optional<User> userToUpdateExists = this.userRepository.findByUuid(UUID.fromString(uuid));

    if (!userToUpdateExists.isPresent()) {
      throw new NotFoundException("Usuário");
    }

    User model = userToUpdateExists.get();

    model.setEmail(userToUpdate.getEmail());
    model.setName(userToUpdate.getName());
    model.setRole(RoleEnum.fromString(userToUpdate.getRole()));

    // TODO: add reset password

    model = this.userRepository.save(model);
    
    return model.getId();
  }
}
