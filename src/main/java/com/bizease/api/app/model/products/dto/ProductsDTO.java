package com.bizease.api.app.model.products.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class ProductsDTO {
    
    private Long id;
    private String uuid;
    private String name;
    private Integer unit;
    private Integer minimumStock;
    private String location;
    private LocalDate expirationDate;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private Long commerceId;
    private Long categoryId;

}
