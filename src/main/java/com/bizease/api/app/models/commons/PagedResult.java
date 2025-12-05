package com.bizease.api.app.models.commons;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PagedResult<T> {

    private List<T> data;
    private Long totalCount;
    private int pageIndex;
    private int pageSize;

    public PagedResult(List<T> items, Long totalCount, int pageIndex, int pageSize) {
        this.data = items;
        this.totalCount = totalCount;
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
    }

    public PagedResult(List<T> items, Long totalCount, int pageIndex) {
        this.data = items;
        this.totalCount = totalCount;
        this.pageIndex = pageIndex;
        this.pageSize = 5;
    }

}
