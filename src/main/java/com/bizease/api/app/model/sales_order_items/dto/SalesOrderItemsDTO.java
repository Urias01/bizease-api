package com.bizease.api.app.model.sales_order_items.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SalesOrderItemsDTO {

    private Integer quantity;
    private BigDecimal unitPrice;
    private String status;
    private String productUuid;
    private String salesOrderUuid;

}
