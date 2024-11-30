package com.bizease.api.app.model.user.dto;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.bizease.api.app.model.commerce.entities.Commerce;
import com.bizease.api.app.model.user.entities.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDTO {

  private Long id;
  private UUID uuid;
  private String name;
  private String email;
  private String isActive;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  private Commerce commerce;

  public UserResponseDTO(User user) {
    this.id = user.getId();
    this.uuid = user.getUuid();
    this.email = user.getEmail();
    this.name = user.getName();
    this.isActive = user.getIsActive().name();
    this.createdAt = user.getCreatedAt();
    this.updatedAt = user.getUpdatedAt();
    this.commerce = user.getCommerce();
  }

  public static List<UserResponseDTO> toList(List<User> users) {
    if (users == null) {
      return Collections.emptyList();
    }

    List<UserResponseDTO> responses = users.stream().map(UserResponseDTO::new).collect(Collectors.toList());

    return responses;
  }

}
