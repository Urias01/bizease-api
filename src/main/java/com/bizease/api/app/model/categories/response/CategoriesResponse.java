package com.bizease.api.app.model.categories.response;

import com.bizease.api.app.model.commerce.entities.Commerce;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoriesResponse {
  
  private String name;
  private String description;
  private Commerce commerce;

}
