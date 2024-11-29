package com.bizease.api.app.model.movements.filter;

import java.time.LocalDate;

import com.bizease.api.app.model.commons.BasePagination;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MovementFilter extends BasePagination {

  private Long id;

  private String type;

  private String commerceUuid;

  private LocalDate movementDate;

}
