package com.bizease.api.app.model.user.useCases;

import com.bizease.api.app.exceptions.UserNotFoundException;
import com.bizease.api.app.model.user.dto.UpdateUserRequestDTO;
import com.bizease.api.app.model.user.entities.User;
import com.bizease.api.app.model.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UpdateUserUseCase {

    @Autowired
    private UserRepository userRepository;

    public Optional<User> updateUser(String uuid, UpdateUserRequestDTO updateUserRequestDTO) {
        Optional<User> verifyUser = this.userRepository.findByUuid(UUID.fromString(uuid));

        if(verifyUser.isPresent()) {
            User user = verifyUser.get();
            user.setName(updateUserRequestDTO.name());
            user.setEmail(updateUserRequestDTO.email());
            user.setPassword(updateUserRequestDTO.password());

            this.userRepository.save(user);
            return Optional.of(user);
        } else {
            throw new UserNotFoundException("Usuário não encontrado!");
        }
    }
}
