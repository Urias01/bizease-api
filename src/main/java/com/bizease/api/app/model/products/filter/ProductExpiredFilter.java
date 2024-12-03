package com.bizease.api.app.model.products.filter;

import com.bizease.api.app.model.commons.BasePagination;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductExpiredFilter extends BasePagination {

  private String commerceUuid;

  private String name;

  private Long id;

}
