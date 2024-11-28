package com.bizease.api.app.model.commerce.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CommerceDTO {
  
  private Long id;
  private UUID uuid;
  private String cnpj;
  private String name;
  private String address;
  private String addressNumber;
  private String neighborhood;
  private String city;
  private String uf;
  private String postalCode;
  private String phoneNumber;
  private Integer isActive;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
  
}
