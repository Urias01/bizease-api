package com.bizease.api.app.model.sales_orders.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.validator.constraints.Length;

import com.bizease.api.app.model.commerce.entities.Commerce;
import com.bizease.api.app.model.sales_orders.dto.SalesOrdersDTO;

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

@Entity(name = "sales_orders")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SalesOrders {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @UuidGenerator
    private String uuid;

    @Length(max = 30)
    private String status;
    private LocalDate orderDate;
    private LocalDate deliveryDate;

    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "com_id", referencedColumnName = "id")
    private Commerce commerce;

    public SalesOrders(SalesOrdersDTO salesOrdersDTO, Commerce commerce) {
        this.status = salesOrdersDTO.getStatus();
        this.orderDate = salesOrdersDTO.getOrderDate();
        this.deliveryDate = salesOrdersDTO.getDeliveryDate();
        this.createdAt = salesOrdersDTO.getCreatedAt();
        this.updatedAt = salesOrdersDTO.getUpdatedAt();
        this.commerce = commerce;
    }

}
