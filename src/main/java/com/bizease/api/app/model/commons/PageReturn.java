package com.bizease.api.app.model.commons;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageReturn<T> {
    
    private T data;
    private Long total;

    public PageReturn(T responses, Long total) {
        this.data = responses;
        this.total = total;
    }

}
