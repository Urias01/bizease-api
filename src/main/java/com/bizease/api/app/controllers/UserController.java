package com.bizease.api.app.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bizease.api.app.models.request.UpdateUserRequest;
import com.bizease.api.app.models.request.UserRequest;
import com.bizease.api.app.models.response.ApiResponse;
import com.bizease.api.app.models.response.UserResponse;
import com.bizease.api.app.services.users.CreateUser;
import com.bizease.api.app.services.users.DeleteUser;
import com.bizease.api.app.services.users.GetMe;
import com.bizease.api.app.services.users.GetUserById;
import com.bizease.api.app.services.users.UpdateUser;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

  private final GetMe getMe;
  private final CreateUser createUser;
  private final GetUserById getUserById;
  private final UpdateUser updateUser;
  private final DeleteUser deleteUser;

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

  @PutMapping("/{uuid}")
  public ResponseEntity<ApiResponse<String>> updateUser(@PathVariable String id,
      @RequestBody UpdateUserRequest request) {
    String updatedId = updateUser.execute(id, request);
    return ResponseEntity.ok(ApiResponse.success(updatedId));
  }

  @DeleteMapping("/{uuid}")
  public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable String uuid) {
    deleteUser.execute(uuid);
    return ResponseEntity.ok(ApiResponse.success(null));
  }
}
