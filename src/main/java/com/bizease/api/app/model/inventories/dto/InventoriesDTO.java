package com.bizease.api.app.model.inventories.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InventoriesDTO {

    private Integer quantity;
    private String location;
    private Long commerceId;
}
