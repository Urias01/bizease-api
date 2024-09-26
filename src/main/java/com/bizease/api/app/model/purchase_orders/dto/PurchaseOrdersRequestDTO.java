package com.bizease.api.app.model.purchase_orders.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseOrdersRequestDTO {

    private String status;
    private LocalDate orderDate;
    private LocalDate expectedDeliveryDate;
    private LocalDate deliveryDate;
    private Long supplierId;
    private Long commerceId;
}
