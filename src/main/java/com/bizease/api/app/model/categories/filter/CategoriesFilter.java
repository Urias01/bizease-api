package com.bizease.api.app.model.categories.filter;

import com.bizease.api.app.model.commons.BasePagination;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoriesFilter extends BasePagination {

  private Long id;

  private String name;

  private String commerceUuid;

}