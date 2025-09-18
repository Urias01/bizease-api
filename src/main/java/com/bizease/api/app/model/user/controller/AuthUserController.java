package com.bizease.api.app.model.user.controller;

import com.bizease.api.app.email.service.EmailService;
import com.bizease.api.app.model.user.dto.AuthUserRequestDTO;
import com.bizease.api.app.model.user.dto.AuthUserResponseDTO;
import com.bizease.api.app.model.user.dto.EmailRequestDTO;
import com.bizease.api.app.model.user.useCases.AuthUserUseCase;
import com.bizease.api.app.model.user.useCases.ResetUserPasswordUseCase;
import com.bizease.api.app.responses.ApiResponse;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthUserController {

    @Autowired
    private AuthUserUseCase authUserUseCase;

    @Autowired
    private EmailService emailService;

    @Autowired
    private ResetUserPasswordUseCase resetUserPasswordUseCase;

    @PostMapping("/users")
    public ResponseEntity<ApiResponse<AuthUserResponseDTO>> authUser(@RequestBody AuthUserRequestDTO authUserRequestDTO)
            throws AuthenticationException {
        AuthUserResponseDTO result = this.authUserUseCase.authenticate(authUserRequestDTO);
        return ResponseEntity.ok().body(ApiResponse.success(result, 200));
    }

    @PostMapping("/password-reset")
    public ResponseEntity<ApiResponse<String>> resetPassword(@RequestBody EmailRequestDTO emailRequest) {
        String newPassword = resetUserPasswordUseCase.resetUserPassword(emailRequest.getEmail());
        emailService.sendPasswordResetEmail(emailRequest.getEmail(), newPassword);
        return ResponseEntity.ok().body(ApiResponse.success("Uma nova senha foi enviada para o seu email!", 200));
    }
}
