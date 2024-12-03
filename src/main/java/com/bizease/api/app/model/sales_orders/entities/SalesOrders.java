package com.bizease.api.app.model.sales_orders.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.bizease.api.app.model.sales_orders.enums.SalesOrderStatus;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;

import com.bizease.api.app.model.commerce.entities.Commerce;
import com.bizease.api.app.model.sales_order_items.entities.SalesOrderItems;
import com.bizease.api.app.model.sales_orders.dto.SalesOrdersDTO;

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

    @Enumerated(EnumType.STRING)
    private SalesOrderStatus status;
    private LocalDate orderDate;
    private LocalDate deliveryDate;

    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "com_id", referencedColumnName = "id")
    private Commerce commerce;

    @OneToMany(mappedBy = "salesOrders")
    private List<SalesOrderItems> salesOrderItems;

    public SalesOrders(SalesOrdersDTO salesOrdersDTO, Commerce commerce) {
        this.status = SalesOrderStatus.valueOf(salesOrdersDTO.getStatus());
        this.orderDate = salesOrdersDTO.getOrderDate();
        this.deliveryDate = salesOrdersDTO.getDeliveryDate();
        this.commerce = commerce;
    }

}
