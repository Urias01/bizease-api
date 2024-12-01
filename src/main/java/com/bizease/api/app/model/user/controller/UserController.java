package com.bizease.api.app.model.user.controller;

import com.bizease.api.app.exceptions.InvalidPasswordException;
import com.bizease.api.app.model.commons.PageReturn;
import com.bizease.api.app.model.user.dto.*;
import com.bizease.api.app.model.user.entities.User;
import com.bizease.api.app.model.user.filters.UserFilter;
import com.bizease.api.app.model.user.useCases.*;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

    @Autowired
    private ChangePasswordUseCase changePasswordUseCase;

    @Autowired
    private UpdateUserNameAndEmailUseCase updateUserNameAndEmailUseCase;

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_OWNER')")
    public PageReturn<List<UserResponseDTO>> getAllUsers(UserFilter filter, HttpServletRequest request) {
        String commerceUuid = (String) request.getAttribute("commerce_uuid");
        filter.setCommerceUuid(commerceUuid);
        return getAllUsersUseCase.execute(filter);
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
    public ResponseEntity<UserResponseDTO> getUserByUuid(@PathVariable UUID uuid, HttpServletRequest request) {
        String commerceUuid = (String) request.getAttribute("commerce_uuid");
        UserResponseDTO userResponseDTO = getUserByUuidUseCase.getUserByUuid(uuid, commerceUuid);
        return ResponseEntity.ok(userResponseDTO);
    }

    @GetMapping("/see-my-profile")
    public ResponseEntity<UserResponseDTO> seeMyProfile(HttpServletRequest request) {
        String userUuidString = (String) request.getAttribute("user_uuid");
        String commerceUuid = (String) request.getAttribute("commerce_uuid");
        UUID userUuid = UUID.fromString(userUuidString);

        var result = getUserByUuidUseCase.getUserByUuid(userUuid, commerceUuid);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/create-employee")
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
    public ResponseEntity<Object> managerUser(@PathVariable String uuid,
            @RequestBody UpdateUserRequestDTO updateUserRequestDTO,
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

    @PatchMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestBody PasswordChangeDTO passwordChangeDTO,
            HttpServletRequest request) {
        try {
            String userUuidString = (String) request.getAttribute("user_uuid");
            UUID userUuid = UUID.fromString(userUuidString);
            changePasswordUseCase.changePassword(userUuid, passwordChangeDTO);

            return ResponseEntity.ok("Sua senha foi alterada com sucesso!");
        } catch (InvalidPasswordException error) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error.getMessage());
        } catch (Exception error) {
            return ResponseEntity.internalServerError().body("Erro ao alterar a senha.");
        }
    }

    @PatchMapping("/change-name-and-email")
    public ResponseEntity<String> changeNameAndEmail(@RequestBody UpdateUserNameAndEmailDTO updateUserNameAndEmailDTO,
            HttpServletRequest request) {
        try {
            String userUuidString = (String) request.getAttribute("user_uuid");
            UUID userUuid = UUID.fromString(userUuidString);
            updateUserNameAndEmailUseCase.execute(userUuid, updateUserNameAndEmailDTO);

            return ResponseEntity.ok("Seus dados foram alterados com sucesso!");
        } catch (Exception error) {
            return ResponseEntity.badRequest().body("Erro ao alterar os dados.");
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
