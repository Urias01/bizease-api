package com.bizease.api.app.model.user.controller;

import com.bizease.api.app.model.user.dto.FirstUserAccessDTO;
import com.bizease.api.app.model.user.dto.UpdateUserRequestDTO;
import com.bizease.api.app.model.user.dto.UserRequestDTO;
import com.bizease.api.app.model.user.dto.UserResponseDTO;
import com.bizease.api.app.model.user.entities.User;
import com.bizease.api.app.model.user.useCases.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private CreateFirstUserAccessUseCase createFirstUserAccessUseCase;

    @Autowired
    private CreateUserUseCase createUserUseCase;

    @Autowired
    private UpdateUserUseCase updateUserUseCase;

    @Autowired
    private DeleteUserUseCase deleteUserUseCase;

    @Autowired
    private GetAllUsersUseCase getAllUsersUseCase;

    @Autowired
    private GetUserByUuidUseCase getUserByUuidUseCase;

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        List<UserResponseDTO> users = getAllUsersUseCase.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @PostMapping("/first-access")
    public ResponseEntity<Object> createFirstAccess(@RequestBody FirstUserAccessDTO firstUserAccessDTO) {
        try {
            var result = this.createFirstUserAccessUseCase.execute(firstUserAccessDTO);
            return ResponseEntity.status(201).body(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<UserResponseDTO> getUserByUuid(@PathVariable String uuid) {
        UserResponseDTO userResponseDTO = getUserByUuidUseCase.getUserByUuid(uuid);
        return ResponseEntity.ok(userResponseDTO);
    }

    @PostMapping
    public ResponseEntity<Object> createUser(@RequestBody UserRequestDTO userRequestDTO) {
        try {
            User newUser = createUserUseCase.createUser(userRequestDTO);
            return ResponseEntity.status(201).body(newUser);
        } catch (Exception error) {
            return ResponseEntity.badRequest().body(error.getMessage());
        }
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<Object> updateUser(@PathVariable String uuid,
            @RequestBody UpdateUserRequestDTO updateUserRequestDTO) {
        try {
            Optional<User> updatedUser = this.updateUserUseCase.updateUser(uuid, updateUserRequestDTO);
            return ResponseEntity.status(201).body(updatedUser);
        } catch (Exception error) {
            return ResponseEntity.badRequest().body(error.getMessage());
        }
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Object> deleteUser(@PathVariable String uuid) {
        try {
            deleteUserUseCase.deleteUser(uuid);
            return ResponseEntity.noContent().build();
        } catch (Exception error) {
            return ResponseEntity.badRequest().body(error.getMessage());
        }
    }
}
