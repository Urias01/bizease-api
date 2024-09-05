package com.bizease.api.app.model.categories.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CategoriesRequest {
  
  private String name;

  private String description;
  
  private Long commerceId;

}
