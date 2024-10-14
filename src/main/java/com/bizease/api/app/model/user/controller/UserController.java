package com.bizease.api.app.model.user.controller;

import com.bizease.api.app.model.user.dto.FirstUserAccessDTO;
import com.bizease.api.app.model.user.dto.UpdateUserRequestDTO;
import com.bizease.api.app.model.user.dto.CreateUserRequestDTO;
import com.bizease.api.app.model.user.dto.UserResponseDTO;
import com.bizease.api.app.model.user.entities.User;
import com.bizease.api.app.model.user.useCases.*;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @Autowired
    private ManagerUsersUseCase managerUsersUseCase;

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_OWNER')")
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
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_OWNER')")
    public ResponseEntity<UserResponseDTO> getUserByUuid(@PathVariable String uuid) {
        UserResponseDTO userResponseDTO = getUserByUuidUseCase.getUserByUuid(uuid);
        return ResponseEntity.ok(userResponseDTO);
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_OWNER')")
    public ResponseEntity<Object> createUser(@RequestBody CreateUserRequestDTO createUserRequestDTO,
            HttpServletRequest request) {
        try {
            String commerceUuid = (String) request.getAttribute("commerce_uuid");
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            String role = authentication.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .filter(r -> r.startsWith("ROLE_"))
                    .map(r -> r.replace("ROLE_", "")) // Remove o prefixo "ROLE_"
                    .findFirst()
                    .orElse("EMPLOYEE");

            User newUser = createUserUseCase.createUser(createUserRequestDTO, commerceUuid, role);
            return ResponseEntity.status(201).body(newUser);
        } catch (Exception error) {
            return ResponseEntity.badRequest().body(error.getMessage());
        }
    }

    @PutMapping("/{uuid}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_OWNER')")
    // TODO: O usuário pode alterar o próprio perfil só não pode trocar de comércio
    public ResponseEntity<Object> updateUser(@PathVariable String uuid,
            @RequestBody UpdateUserRequestDTO updateUserRequestDTO) {
        try {
            Optional<User> updatedUser = this.updateUserUseCase.updateUser(uuid, updateUserRequestDTO);
            return ResponseEntity.status(201).body(updatedUser);
        } catch (Exception error) {
            return ResponseEntity.badRequest().body(error.getMessage());
        }
    }

    @PutMapping("/{uuid}/manager")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_OWNER')")
    public ResponseEntity<Object> managerUser(@PathVariable String uuid, @RequestBody UpdateUserRequestDTO updateUserRequestDTO,
            HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String role = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .filter(r -> r.startsWith("ROLE_"))
                .map(r -> r.replace("ROLE_", "")) // Remove o prefixo "ROLE_"
                .findFirst()
                .orElse("EMPLOYEE");
        try {
            this.managerUsersUseCase.execute(uuid, role, updateUserRequestDTO);
            return ResponseEntity.ok().build();
        } catch (Exception error) {
            return ResponseEntity.badRequest().body(error.getMessage());
        }
    }

    @DeleteMapping("/{uuid}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_OWNER')")
    public ResponseEntity<Object> deleteUser(@PathVariable String uuid) {
        try {
            deleteUserUseCase.deleteUser(uuid);
            return ResponseEntity.noContent().build();
        } catch (Exception error) {
            return ResponseEntity.badRequest().body(error.getMessage());
        }
    }
}
