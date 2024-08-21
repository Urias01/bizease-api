package com.bizease.api.app.model.user.useCases;

import com.bizease.api.app.exceptions.UserAlreadyExistsException;
import com.bizease.api.app.model.user.entities.User;
import com.bizease.api.app.model.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CreateUserUseCase {

    @Autowired
    private UserRepository userRepository;

    public User createUser(User user) {
        Optional<User> verifyUser = this.userRepository.findByEmail(user.getEmail());

        if(verifyUser.isPresent()) {
            throw new UserAlreadyExistsException("Usuário já existente com este email!");
        } else {
            return this.userRepository.save(user);
        }
    }
}
