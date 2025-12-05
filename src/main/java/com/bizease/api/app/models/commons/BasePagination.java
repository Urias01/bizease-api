package com.bizease.api.app.models.commons;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BasePagination {

    private Integer page;

    private Integer size;

    private String field = "id";

    private String direction = "asc";

}
