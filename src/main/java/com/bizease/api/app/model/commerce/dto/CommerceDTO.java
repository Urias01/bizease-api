package com.bizease.api.app.model.commerce.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CommerceDTO {
  
  private Long id;
  private String uuid;
  private String cnpj;
  private String name;
  private String postalCode;
  private String address;
  private String addressNumber;
  private String neighborhood;
  private String city;
  private String uf;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
  
}
