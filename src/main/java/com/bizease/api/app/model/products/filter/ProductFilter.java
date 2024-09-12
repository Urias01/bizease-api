package com.bizease.api.app.model.products.filter;

import com.bizease.api.app.model.commons.BasePagination;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductFilter extends BasePagination {
    
    private String name;

    private Integer unit;

    private Integer minimumStock;

    private String commerceUuid;

}
