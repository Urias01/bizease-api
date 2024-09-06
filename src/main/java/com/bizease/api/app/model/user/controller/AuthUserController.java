package com.bizease.api.app.model.user.controller;

import com.bizease.api.app.model.user.dto.AuthUserRequestDTO;
import com.bizease.api.app.model.user.useCases.AuthUserUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.AuthenticationException;

@RestController
@RequestMapping("/auth")
public class AuthUserController {

    @Autowired
    private AuthUserUseCase authUserUseCase;

    @PostMapping("/users")
    public ResponseEntity<Object> authUser(@RequestBody AuthUserRequestDTO authUserRequestDTO) {
        try {
            var result = this.authUserUseCase.authenticate(authUserRequestDTO);
            return ResponseEntity.ok(result);
        } catch (Exception error) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error.getMessage());
        }
    }
}
