package com.bizease.api.app.model.sales_orders.dto;

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
public class SalesOrdersDTO {

    private String status;
    private LocalDate orderDate;
    private LocalDate deliveryDate;
    private String commerceUuid;
    
}
