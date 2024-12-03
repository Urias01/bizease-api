package com.bizease.api.app.model.sales_orders.filter;

import com.bizease.api.app.model.commons.BasePagination;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SalesOrderFilter extends BasePagination {

  private Long id;

  private String commerceUuid;

  private String status;

}
