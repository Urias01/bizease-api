package com.bizease.api.app.model.user.controller;

import com.bizease.api.app.email.service.EmailService;
import com.bizease.api.app.model.user.dto.AuthUserRequestDTO;
import com.bizease.api.app.model.user.dto.EmailRequestDTO;
import com.bizease.api.app.model.user.useCases.AuthUserUseCase;
import com.bizease.api.app.model.user.useCases.ResetUserPasswordUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<Object> authUser(@RequestBody AuthUserRequestDTO authUserRequestDTO) {
        try {
            var result = this.authUserUseCase.authenticate(authUserRequestDTO);
            return ResponseEntity.ok(result);
        } catch (Exception error) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error.getMessage());
        }
    }

    @PostMapping("/password-reset")
    public ResponseEntity<String> resetPassword(@RequestBody EmailRequestDTO emailRequest) {
        try {
            String newPassword = resetUserPasswordUseCase.resetUserPassword(emailRequest.getEmail());
            emailService.sendPasswordResetEmail(emailRequest.getEmail(), newPassword);
            return ResponseEntity.ok("Uma nova senha foi enviada para o seu email!");
        } catch (Exception error) {
            return ResponseEntity.badRequest().body(error.getMessage());
        }
    }
}
