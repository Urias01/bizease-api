package com.bizease.api.app.model.purchase_order_items.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseOrderItemDTO {

    private Integer quantity;
    private BigDecimal unit_price;
    private BigDecimal unit_selling_price;
    private Long productId;
    private Long purchaseOrderId;
}
