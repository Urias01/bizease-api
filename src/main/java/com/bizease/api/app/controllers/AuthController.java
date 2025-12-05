package com.bizease.api.app.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bizease.api.app.models.request.AuthRequest;
import com.bizease.api.app.models.response.ApiResponse;
import com.bizease.api.app.models.response.AuthResponse;
import com.bizease.api.app.services.auth.SignIn;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/auth")
public class AuthController {

  private final SignIn signIn;

  public AuthController(SignIn signIn) {
      this.signIn = signIn;
  }

  @PostMapping("/sign-in")
  public ResponseEntity<ApiResponse<AuthResponse>> signIn(@RequestBody AuthRequest request) {
      AuthResponse token = signIn.execute(request);
      return ResponseEntity.ok(ApiResponse.success(token));
  }

}
