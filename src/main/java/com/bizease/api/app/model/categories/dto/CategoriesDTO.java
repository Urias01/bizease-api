package com.bizease.api.app.model.categories.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoriesDTO {
  
  private String name;
  private String description;
  private Long commerceId;


}
