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
    private LocalDate order_date;
    private LocalDate expected_delivery_date;
    private Long sup_id;
    private Long com_id;
}
