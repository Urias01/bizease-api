package com.bizease.api.app.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bizease.api.app.models.request.UserRequest;
import com.bizease.api.app.models.response.ApiResponse;
import com.bizease.api.app.models.response.UserResponse;
import com.bizease.api.app.useCases.users.CreateUser;
import com.bizease.api.app.useCases.users.GetMe;
import com.bizease.api.app.useCases.users.GetUserById;

@RestController
@RequestMapping("/users")
public class UserController {

  private final GetMe getMe;
  private final CreateUser createUser;
  private final GetUserById getUserById;

  public UserController(GetMe getMe, CreateUser createUser, GetUserById getUserById) {
    this.getMe = getMe;
    this.createUser = createUser;
    this.getUserById = getUserById;
  }

  @GetMapping("/me")
  public ResponseEntity<ApiResponse<UserResponse>> getMe() {
    UserResponse user = getMe.execute();
    return ResponseEntity.ok(ApiResponse.success(user));
  }

  @PostMapping
  public ResponseEntity<ApiResponse<String>> createUser(@RequestBody UserRequest request) {
    String user = createUser.execute(request);
    return ResponseEntity.ok(ApiResponse.success(user));
  }

  @GetMapping("/{uuid}")
  public ResponseEntity<ApiResponse<UserResponse>> getUserById(@PathVariable String uuid) {
    UserResponse user = getUserById.execute(uuid);
    return ResponseEntity.ok(ApiResponse.success(user));
  }
}
