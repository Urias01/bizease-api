package com.bizease.api.app.model.user.controller;

import com.bizease.api.app.model.user.dto.UpdateUserRequestDTO;
import com.bizease.api.app.model.user.dto.UserRequestDTO;
import com.bizease.api.app.model.user.entities.User;
import com.bizease.api.app.model.user.repository.UserRepository;
import com.bizease.api.app.model.user.useCases.CreateUserUseCase;
import com.bizease.api.app.model.user.useCases.UpdateUserUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CreateUserUseCase createUserUseCase;

    @Autowired
    private UpdateUserUseCase updateUserUseCase;

    @PostMapping
    public ResponseEntity<Object> createUser(@RequestBody UserRequestDTO userRequestDTO) {
        try {
            User user = new User();
            user.setName(userRequestDTO.getName());
            user.setEmail(userRequestDTO.getEmail());
            user.setPassword(userRequestDTO.getPassword());

            var result = this.createUserUseCase.createUser(user);
            return ResponseEntity.status(201).body(result);
        } catch (Exception error) {
            return ResponseEntity.badRequest().body(error.getMessage());
        }
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<Object> updateUser(@PathVariable String uuid, @RequestBody UpdateUserRequestDTO updateUserRequestDTO) {
        try {
            Optional<User> updatedUser = this.updateUserUseCase.updateUser(uuid, updateUserRequestDTO);
            return ResponseEntity.status(201).body(updatedUser);
        } catch (Exception error) {
            return ResponseEntity.badRequest().body(error.getMessage());
        }
    }
}
