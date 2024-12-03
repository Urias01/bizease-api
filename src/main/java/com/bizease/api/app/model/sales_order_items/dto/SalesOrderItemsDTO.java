package com.bizease.api.app.model.sales_order_items.dto;

import java.math.BigDecimal;
import java.util.List;

import com.bizease.api.app.model.sales_order_items.entities.SalesOrderItems;
import com.bizease.api.app.model.sales_orders.entities.SalesOrders;

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

    public SalesOrderItemsDTO(SalesOrderItems salesItem) {
        this.quantity = salesItem.getQuantity();
        this.unitPrice = salesItem.getUnitPrice();
        this.status = salesItem.getStatus().name();
        this.productUuid = salesItem.getProducts().getUuid();
        this.salesOrderUuid = salesItem.getSalesOrders().getUuid();    
    }

    public SalesOrderItemsDTO(SalesOrders c) {
        //TODO Auto-generated constructor stub
    }

}
