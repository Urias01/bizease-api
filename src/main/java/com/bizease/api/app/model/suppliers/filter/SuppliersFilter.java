package com.bizease.api.app.model.suppliers.filter;

import com.bizease.api.app.model.commons.BasePagination;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SuppliersFilter extends BasePagination {
    
    private String name;

    private String commerceUuid;

}
