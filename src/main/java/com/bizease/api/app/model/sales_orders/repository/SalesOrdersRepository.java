package com.bizease.api.app.model.sales_orders.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.bizease.api.app.model.sales_orders.entities.SalesOrders;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SalesOrdersRepository extends JpaRepository<SalesOrders, Long>, JpaSpecificationExecutor<SalesOrders> {

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
            COALESCE(SUM(DISTINCT poi_total), 0) AS buying_total,
            COALESCE(SUM(DISTINCT soi_total), 0) AS selling_total
        FROM
            generate_series(
                DATE_TRUNC('year', CURRENT_DATE),
                DATE_TRUNC('year', CURRENT_DATE) + INTERVAL '11 months',
                INTERVAL '1 month'
            ) AS months(month_date) -- Aqui foi adicionado o alias "months" com a coluna "month_date"
        LEFT JOIN (
            SELECT
                DATE_TRUNC('month', po.order_date) AS order_month,
                SUM(poi.quantity * poi.unit_price) AS poi_total,
                po.com_id
            FROM
                purchase_orders po
            JOIN
                purchase_order_items poi ON poi.por_id = po.id
            WHERE
                po.com_id = :comId
            GROUP BY
                order_month, po.com_id
        ) purchase_totals
        ON months.month_date = purchase_totals.order_month
        LEFT JOIN (
            SELECT
                DATE_TRUNC('month', so.order_date) AS order_month,
                SUM(soi.quantity * soi.unit_price) AS soi_total,
                so.com_id
            FROM
                sales_orders so
            JOIN
                sales_order_items soi ON soi.sor_id = so.id
            WHERE
                so.com_id = :comId
            GROUP BY
                order_month, so.com_id
        ) sales_totals
        ON months.month_date = sales_totals.order_month
        GROUP BY
            months.month_date
        ORDER BY
            months.month_date;
                """, nativeQuery = true)
    List<Object[]> findAnnualBuyingAndSelling(@Param("comId") Long comId);
}