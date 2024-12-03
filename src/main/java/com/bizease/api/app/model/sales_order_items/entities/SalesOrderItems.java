package com.bizease.api.app.model.sales_order_items.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.bizease.api.app.model.sales_order_items.enums.SalesOrderItemStatus;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;

import com.bizease.api.app.model.products.entities.Products;
import com.bizease.api.app.model.sales_order_items.dto.SalesOrderItemsDTO;
import com.bizease.api.app.model.sales_orders.entities.SalesOrders;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "sales_order_items")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SalesOrderItems {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @UuidGenerator
    private String uuid;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false, precision = 7, scale = 2)
    private BigDecimal unitPrice;

    @Enumerated(EnumType.STRING)
    private SalesOrderItemStatus status;

    @CreationTimestamp
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "prod_id", referencedColumnName = "id")
    private Products products;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sor_id", referencedColumnName = "id")
    private SalesOrders salesOrders;

    public SalesOrderItems (SalesOrderItemsDTO salesOrderItemsDTO, Products products, SalesOrders salesOrders) {
        this.quantity = salesOrderItemsDTO.getQuantity();
        this.unitPrice = salesOrderItemsDTO.getUnitPrice();
        this.status = SalesOrderItemStatus.valueOf(salesOrderItemsDTO.getStatus());
        this.products = products;
        this.salesOrders = salesOrders;
    }
    
}
