package com.bizease.api.app.model.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FirstUserAccessDTO {
 
  private String name;
  private String email;
  private String password;
  private String commerceName;
  private String cnpj;
  
}
