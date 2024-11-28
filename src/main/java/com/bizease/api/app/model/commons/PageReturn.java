package com.bizease.api.app.model.commons;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageReturn<T> {

    private T data;
    private Long totalCount;
    private int pageIndex;
    private int perPage;

    public PageReturn(T responses, Long total, int pageIndex, int perPage) {
        this.data = responses;
        this.totalCount = total;
        this.pageIndex = pageIndex;
        this.perPage = perPage;
    }

    public PageReturn(T responses, Long total, int pageIndex) {
        this.data = responses;
        this.totalCount = total;
        this.pageIndex = pageIndex;
        this.perPage = 5;
    }

}
