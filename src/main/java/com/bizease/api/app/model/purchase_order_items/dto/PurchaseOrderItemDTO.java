package com.bizease.api.app.model.purchase_order_items.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseOrderItemDTO {

    private Integer quantity;
    private BigDecimal unit_price;
    private LocalDate expirationDate;
    private Long productId;
    private Long purchaseOrderId;
}
