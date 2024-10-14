package com.bizease.api.app.model.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserRequestDTO {

  Long id;
  String uuid;
  String name;
  String email;
  String password;
  Boolean resetPassword;
  String role;
  Integer isActive;

}
