package com.bizease.api.app.model.sales_orders.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bizease.api.app.model.sales_orders.entities.SalesOrders;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SalesOrdersRepository extends JpaRepository<SalesOrders, Long> {

    Optional<SalesOrders> findByUuid(String uuid);

    @Query(value = """
        SELECT
            DATE(so.order_date) AS date,
            SUM(soi.quantity * soi.unit_price) AS receipt
        FROM
            sales_orders so
        JOIN
            sales_order_items soi ON so.id = soi.sor_id
        WHERE
            so.com_id = :comId
            AND so.order_date BETWEEN :startDate and :endDate
        GROUP BY
            DATE(so.order_date)
        ORDER BY
            date        
        """, nativeQuery = true)
    List<Map<String, Object>> findRevenueByPeriod(@Param("comId") Long comId,
                                                  @Param("startDate") LocalDate startDate,
                                                  @Param("endDate") LocalDate endDate);
}