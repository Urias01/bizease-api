package com.bizease.api.app.model.sales_order_items.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bizease.api.app.model.sales_order_items.entities.SalesOrderItems;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SalesOrderItemsRepository extends JpaRepository<SalesOrderItems, Long> {

    Optional<SalesOrderItems> findByUuid(String uuid);

    @Query(value = """
            SELECT
                p.name AS product,
                SUM(soi.quantity) AS quantity,
                SUM(soi.quantity * soi.unit_price) AS priceTotal
            FROM
                sales_order_items soi
            JOIN
                products p ON soi.prod_id = p.id
            JOIN
                sales_orders so ON soi.sor_id = so.id
            WHERE
                so.com_id = :comId
                AND soi.status = 'DANIFICADO'
            GROUP BY
                p.name
            ORDER BY
                quantity DESC            
            """, nativeQuery = true)
    List<Map<String, Object>> findLostProducts(@Param("comId") Long comId);
}
