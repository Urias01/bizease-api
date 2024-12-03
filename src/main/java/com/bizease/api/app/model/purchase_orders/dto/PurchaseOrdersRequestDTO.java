package com.bizease.api.app.model.purchase_orders.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

import com.bizease.api.app.model.purchase_order_items.dto.PurchaseOrderItemDTO;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseOrdersRequestDTO {

    private String status;
    private LocalDate orderDate;
    private LocalDate expectedDeliveryDate;
    private LocalDate deliveryDate;
    private String supplierUuid;
    private String commerceUuid;
    private List<PurchaseOrderItemDTO> purcharseOrderItems;
}
