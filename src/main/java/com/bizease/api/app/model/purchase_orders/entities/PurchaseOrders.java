package com.bizease.api.app.model.purchase_orders.entities;

import com.bizease.api.app.model.commerce.entities.Commerce;
import com.bizease.api.app.model.purchase_order_items.entities.PurchaseOrderItem;
import com.bizease.api.app.model.purchase_orders.enums.StatusEnum;
import com.bizease.api.app.model.suppliers.entities.Suppliers;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "purchase_orders")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseOrders {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @UuidGenerator()
    @Column(unique = true, nullable = false)
    private UUID uuid;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusEnum status;

    @Column(name = "order_date")
    private LocalDate orderDate;

    @Column(name = "expected_delivery_date")
    private LocalDate expectedDeliveryDate;

    @Column(name = "delivery_date")
    private LocalDate deliveryDate;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @OneToOne
    @JoinColumn(name = "sup_id")
    private Suppliers suppliers;

    @OneToOne
    @JoinColumn(name = "com_id")
    private Commerce commerce;

    @OneToMany(mappedBy = "purchaseOrders")
    private List<PurchaseOrderItem> purchaseOrderItems;
}
