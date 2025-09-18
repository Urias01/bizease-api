package com.bizease.api.app.model.user.controller;

import com.bizease.api.app.exceptions.InvalidPasswordException;
import com.bizease.api.app.model.commons.PageReturn;
import com.bizease.api.app.model.user.dto.*;
import com.bizease.api.app.model.user.filters.UserFilter;
import com.bizease.api.app.model.user.useCases.*;
import com.bizease.api.app.responses.ApiResponse;

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

    @Autowired
    EnableAndDisableUserUseCase enableAndDisableUserUseCase;

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_OWNER')")
    public ResponseEntity<ApiResponse<PageReturn<List<UserResponseDTO>>>> getAllUsers(
            UserFilter filter,
            HttpServletRequest request) {
        String commerceUuid = (String) request.getAttribute("commerce_uuid");
        filter.setCommerceUuid(commerceUuid);
        PageReturn<List<UserResponseDTO>> user = getAllUsersUseCase.execute(filter);
        return ResponseEntity.ok().body(ApiResponse.success(user, 200));
    }

    @PostMapping("/first-access")
    public ResponseEntity<ApiResponse<Object>> createFirstAccess(@RequestBody FirstUserAccessDTO firstUserAccessDTO) {
        var result = this.createFirstUserAccessUseCase.execute(firstUserAccessDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(result, 201));
    }

    @GetMapping("/{uuid}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_OWNER')")
    public ResponseEntity<ApiResponse<UserResponseDTO>> getUserByUuid(
            @PathVariable UUID uuid,
            HttpServletRequest request) {
        String commerceUuid = (String) request.getAttribute("commerce_uuid");
        UserResponseDTO userResponseDTO = getUserByUuidUseCase.getUserByUuid(uuid, commerceUuid);
        return ResponseEntity.ok().body(ApiResponse.success(userResponseDTO, 200));
    }

    @GetMapping("/see-my-profile")
    public ResponseEntity<ApiResponse<UserResponseDTO>> seeMyProfile(HttpServletRequest request) {
        String userUuidString = (String) request.getAttribute("user_uuid");
        String commerceUuid = (String) request.getAttribute("commerce_uuid");
        UUID userUuid = UUID.fromString(userUuidString);
        UserResponseDTO result = getUserByUuidUseCase.getUserByUuid(userUuid, commerceUuid);
        return ResponseEntity.ok().body(ApiResponse.success(result, 200));
    }

    @PostMapping("/create-employee")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_OWNER')")
    public ResponseEntity<ApiResponse<Long>> createUser(
            @RequestBody CreateUserRequestDTO createUserRequestDTO,
            HttpServletRequest request) {
        String commerceUuid = (String) request.getAttribute("commerce_uuid");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String role = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .filter(r -> r.startsWith("ROLE_"))
                .map(r -> r.replace("ROLE_", ""))
                .findFirst()
                .orElse("EMPLOYEE");

        Long userId = createUserUseCase.createUser(createUserRequestDTO, commerceUuid, role);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(userId, 201));
    }

    @PutMapping("/{uuid}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_OWNER')")
    public ResponseEntity<ApiResponse<Long>> updateUser(
            @PathVariable String uuid,
            @RequestBody UpdateUserRequestDTO updateUserRequestDTO) {
        Long userId = this.updateUserUseCase.updateUser(uuid, updateUserRequestDTO);
        return ResponseEntity.ok().body(ApiResponse.success(userId, 200));

    }

    @PutMapping("/{uuid}/manager")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_OWNER')")
    public ResponseEntity<ApiResponse<Long>> managerUser(
            @PathVariable String uuid,
            @RequestBody UpdateUserRequestDTO updateUserRequestDTO,
            HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String role = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .filter(r -> r.startsWith("ROLE_"))
                .map(r -> r.replace("ROLE_", ""))
                .findFirst()
                .orElse("EMPLOYEE");

        Long userId = this.managerUsersUseCase.execute(uuid, role, updateUserRequestDTO);
        return ResponseEntity.ok().body(ApiResponse.success(userId, 200));
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

    @PatchMapping("/{uuid}/enable-or-disable")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_OWNER')")
    public ResponseEntity<ApiResponse<Void>> enableAndDisable(@PathVariable UUID uuid) {
        enableAndDisableUserUseCase.execute(uuid);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ApiResponse.success(null, 204));
    }

    @PatchMapping("/change-name-and-email")
    public ResponseEntity<ApiResponse<Void>> changeNameAndEmail(
            @RequestBody UpdateUserNameAndEmailDTO updateUserNameAndEmailDTO,
            HttpServletRequest request) {
        String userUuidString = (String) request.getAttribute("user_uuid");
        UUID userUuid = UUID.fromString(userUuidString);
        updateUserNameAndEmailUseCase.execute(userUuid, updateUserNameAndEmailDTO);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ApiResponse.success(null, 204));
    }

    @DeleteMapping("/{uuid}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_OWNER')")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable String uuid) {
        deleteUserUseCase.deleteUser(uuid);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ApiResponse.success(null, 204));
    }
}
