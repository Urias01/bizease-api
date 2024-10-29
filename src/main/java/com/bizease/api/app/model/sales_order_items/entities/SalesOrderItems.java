package com.bizease.api.app.model.sales_order_items.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;

import com.bizease.api.app.model.products.entities.Products;
import com.bizease.api.app.model.sales_order_items.dto.SalesOrderItemsDTO;
import com.bizease.api.app.model.sales_orders.entities.SalesOrders;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @UuidGenerator
    private String uuid;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false, precision = 7, scale = 2)
    private BigDecimal unitPrice;

    private String status;

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
        this.status = salesOrderItemsDTO.getStatus();
        this.createdAt = salesOrderItemsDTO.getCreatedAt();
        this.updatedAt = salesOrderItemsDTO.getUpdatedAt();
        this.products = products;
        this.salesOrders = salesOrders;
    }
    
}
