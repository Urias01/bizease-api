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

    @Query(value = """
        SELECT
            TO_CHAR(months.month_date, 'Month') AS month,
            COALESCE(SUM(CASE WHEN po.id IS NOT NULL THEN poi.quantity * poi.unit_price END), 0) AS buyingTotal,
            COALESCE(SUM(CASE WHEN so.id IS NOT NULL THEN soi.quantity * soi.unit_price END), 0) AS sellingTotal
        FROM
            generate_series(
                DATE_TRUNC('month', CURRENT_DATE) - INTERVAL '11 months',
                DATE_TRUNC('month', CURRENT_DATE),
                INTERVAL '1 month'
            ) AS months (month_date)  -- Definir alias como "months"
        LEFT JOIN
            purchase_orders po ON DATE_TRUNC('month', po.order_date) = months.month_date AND po.com_id = :comId
        LEFT JOIN
            purchase_order_items poi ON poi.por_id = po.id
        LEFT JOIN
            sales_orders so ON DATE_TRUNC('month', so.order_date) = months.month_date AND so.com_id = :comId
        LEFT JOIN
            sales_order_items soi ON soi.sor_id = so.id
        GROUP BY
            months.month_date 
        ORDER BY
            months.month_date;  
            """, nativeQuery = true)
    List<Object[]> findAnnualBuyingAndSelling(@Param("comId") Long comId);
}