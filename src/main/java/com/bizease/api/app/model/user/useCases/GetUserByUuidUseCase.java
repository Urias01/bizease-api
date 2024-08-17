package com.bizease.api.app.model.user.useCases;

import com.bizease.api.app.exceptions.UserNotFoundException;
import com.bizease.api.app.model.user.dto.UserResponseDTO;
import com.bizease.api.app.model.user.entities.User;
import com.bizease.api.app.model.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class GetUserByUuidUseCase {

    @Autowired
    private UserRepository userRepository;

    public UserResponseDTO getUserByUuid(String uuid) {
        Optional<User> user = userRepository.findByUuid(UUID.fromString(uuid));

        if (user.isPresent()) {
            User foundUser = user.get();
            return new UserResponseDTO(foundUser.getId(), foundUser.getUuid(), foundUser.getName(),
                    foundUser.getEmail(), foundUser.getCreatedAt(), foundUser.getUpdatedAt());
        } else {
            throw new UserNotFoundException("Usuário não encontrado!");
        }
    }
}
