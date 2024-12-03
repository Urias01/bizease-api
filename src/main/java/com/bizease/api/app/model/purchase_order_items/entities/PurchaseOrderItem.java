package com.bizease.api.app.model.purchase_order_items.entities;

import com.bizease.api.app.model.products.entities.Products;
import com.bizease.api.app.model.purchase_order_items.dto.PurchaseOrderItemDTO;
import com.bizease.api.app.model.purchase_orders.entities.PurchaseOrders;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "purchase_order_items")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseOrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @UuidGenerator()
    @Column(nullable = false)
    private UUID uuid;

    @Column(nullable = false)
    private Integer quantity;

    @Column(name = "unit_price", precision = 7, scale = 2)
    private BigDecimal unitPrice;

    @Column(name = "expiration_date")
    private LocalDate expirationDate;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @OneToOne
    @JoinColumn(name = "prod_id")
    private Products products;

    @OneToOne
    @JoinColumn(name = "por_id")
    private PurchaseOrders purchaseOrders;

    public PurchaseOrderItem(PurchaseOrderItemDTO purchaseOrderItemDTO, Products products,
            PurchaseOrders purchaseOrders) {
        this.quantity = purchaseOrderItemDTO.getQuantity();
        this.unitPrice = purchaseOrderItemDTO.getUnitPrice();
        this.expirationDate = purchaseOrderItemDTO.getExpirationDate();
        this.products = products;
        this.purchaseOrders = purchaseOrders;
    }
}
