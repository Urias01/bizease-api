package com.bizease.api.app.model.sales_order_items.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SalesOrderItemsDTO {
    
    private Long id;
    private String uuid;
    private Integer quantity;
    private BigDecimal unitPrice;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private String productUuid;
    private String salesOrderUuid;

}
