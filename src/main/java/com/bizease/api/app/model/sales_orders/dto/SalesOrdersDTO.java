package com.bizease.api.app.model.sales_orders.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.bizease.api.app.model.sales_order_items.dto.SalesOrderItemsDTO;
import com.bizease.api.app.model.sales_orders.entities.SalesOrders;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SalesOrdersDTO {

    private Long id;
    private String uuid;
    private String status;
    private LocalDate orderDate;
    private LocalDate deliveryDate;
    private String commerceUuid;

    private List<SalesOrderItemsDTO> salesOrdersItems;

}
