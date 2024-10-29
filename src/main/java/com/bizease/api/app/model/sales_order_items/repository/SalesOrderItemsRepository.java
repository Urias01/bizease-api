package com.bizease.api.app.model.sales_order_items.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bizease.api.app.model.sales_order_items.entities.SalesOrderItems;

public interface SalesOrderItemsRepository extends JpaRepository<SalesOrderItems, Long> {

    Optional<SalesOrderItems> findByUuid(String uuid);
    
}
