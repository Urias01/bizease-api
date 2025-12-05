package com.bizease.api.app.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bizease.api.app.models.request.AuthRequest;
import com.bizease.api.app.models.response.ApiResponse;
import com.bizease.api.app.models.response.AuthResponse;
import com.bizease.api.app.services.auth.SignIn;
import com.bizease.api.app.services.auth.SignInAdmin;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/auth")
public class AuthController {

  private final SignIn signIn;
  private final SignInAdmin signInAdmin;

  public AuthController(SignIn signIn, SignInAdmin signInAdmin) {
      this.signIn = signIn;
      this.signInAdmin = signInAdmin;
  }

  @PostMapping("/sign-in")
  public ResponseEntity<ApiResponse<AuthResponse>> signIn(@RequestBody AuthRequest request) {
      AuthResponse token = signIn.execute(request);
      return ResponseEntity.ok(ApiResponse.success(token));
  }

  @PostMapping("/sign-in/admin")
  public ResponseEntity<ApiResponse<AuthResponse>> signInAdmin(@RequestBody AuthRequest request) {
      AuthResponse token = signInAdmin.execute(request);
      return ResponseEntity.ok(ApiResponse.success(token));
  }

}
