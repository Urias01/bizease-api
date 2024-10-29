package com.bizease.api.app.model.sales_orders.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bizease.api.app.model.sales_orders.entities.SalesOrders;

public interface SalesOrdersRepository extends JpaRepository<SalesOrders, Long> {

    Optional<SalesOrders> findByUuid(String uuid);
    
}