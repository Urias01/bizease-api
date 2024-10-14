package com.bizease.api.app.model.user.useCases;

import com.bizease.api.app.exceptions.NotFoundException;
import com.bizease.api.app.model.user.dto.UpdateUserRequestDTO;
import com.bizease.api.app.model.user.entities.User;
import com.bizease.api.app.model.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UpdateUserUseCase {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Optional<User> updateUser(String uuid, UpdateUserRequestDTO updateUserRequestDTO) {
        Optional<User> verifyUser = this.userRepository.findByUuid(UUID.fromString(uuid));

        if(verifyUser.isPresent()) {
            User user = verifyUser.get();
            user.setName(updateUserRequestDTO.getName());
            user.setEmail(updateUserRequestDTO.getEmail());
            user.setPassword(passwordEncoder.encode(updateUserRequestDTO.getPassword()));

            this.userRepository.save(user);
            return Optional.of(user);
        } else {
            throw new NotFoundException("Usuário");
        }
    }
}
