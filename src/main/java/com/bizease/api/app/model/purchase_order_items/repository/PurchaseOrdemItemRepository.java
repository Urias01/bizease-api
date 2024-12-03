package com.bizease.api.app.model.purchase_order_items.repository;

import com.bizease.api.app.model.purchase_order_items.entities.PurchaseOrderItem;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PurchaseOrdemItemRepository extends JpaRepository<PurchaseOrderItem, Long> {
    Optional<PurchaseOrderItem> findByUuid(UUID uuid);

       List<PurchaseOrderItem> findByPurchaseOrdersId(Long purchaseOrdersId);
}
