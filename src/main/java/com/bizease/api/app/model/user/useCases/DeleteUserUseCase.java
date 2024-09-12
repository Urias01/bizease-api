package com.bizease.api.app.model.user.useCases;

import com.bizease.api.app.exceptions.UserNotFoundException;
import com.bizease.api.app.model.user.entities.User;
import com.bizease.api.app.model.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class DeleteUserUseCase {

    @Autowired
    private UserRepository userRepository;

    public void deleteUser(String uuid) {
        Optional<User> verifyUser = userRepository.findByUuid(UUID.fromString(uuid));

        if(verifyUser.isPresent()) {
            userRepository.delete(verifyUser.get());
        } else {
            throw new UserNotFoundException("Usuário não encontrado!");
        }
    }
}
