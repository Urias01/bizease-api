package com.bizease.api.app.model.user.dto;

import java.time.LocalDateTime;
import java.util.UUID;

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
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  public UserResponseDTO(User user) {
    this.id = user.getId();
    this.uuid = user.getUuid();
    this.email = user.getEmail();
    this.name = user.getName();
    this.createdAt = user.getCreatedAt();
    this.updatedAt = user.getUpdatedAt();
  }

}
