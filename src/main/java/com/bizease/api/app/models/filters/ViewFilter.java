package com.bizease.api.app.models.filters;

import com.bizease.api.app.models.commons.BasePagination;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ViewFilter extends BasePagination {

    private String tenantId;
    private Long id;
    private String status;
    private String name;
  
}
