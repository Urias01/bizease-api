package com.bizease.api.app.model.purchase_orders.repository;

import com.bizease.api.app.model.purchase_orders.entities.PurchaseOrders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PurchaseOrdersRepository
        extends JpaRepository<PurchaseOrders, Long>, JpaSpecificationExecutor<PurchaseOrders> {
    Optional<PurchaseOrders> findByUuid(UUID uuid);
}
