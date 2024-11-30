package com.bizease.api.app.model.user.filters;

import com.bizease.api.app.model.commons.BasePagination;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserFilter extends BasePagination {

  private Long id;

  private String name;

  private String email;

  private String isActive;

  private String commerceUuid;

}
