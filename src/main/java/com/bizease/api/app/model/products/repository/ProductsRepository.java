package com.bizease.api.app.model.products.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.bizease.api.app.model.products.entities.Products;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductsRepository extends JpaRepository<Products, Long>, JpaSpecificationExecutor<Products> {

    Optional<Products> findByUuid(String uuid);

    Optional<Products> findByNameAndCommerceUuid(String name, String commerceUuid);

    @Query(value = """
            SELECT
                p.name AS product,
                SUM(soi.quantity) AS amount
            FROM
                products p
            JOIN
                sales_order_items soi ON p.id = soi.prod_id
            JOIN
                sales_orders so ON soi.sor_id = so.id
            WHERE
                so.com_id = :comId
            GROUP BY
                p.name
            ORDER BY
                amount DESC
            LIMIT 5
            """, nativeQuery = true)
    List<Map<String, Object>> findPopularProducts(@Param("comId") Long comId);

    @Query(value = """
            SELECT
                p.id AS product_id,
                p.name AS product_name,
                c.name AS category_name,
                SUM(poi.quantity) AS expired_quantity
            FROM
                products p
            JOIN
                categories c ON p.cat_id = c.id
            JOIN
                purchase_order_items poi ON poi.prod_id = p.id
            WHERE
                poi.expiration_date < CURRENT_DATE
                AND p.com_id = :comId
            GROUP BY
                p.id, p.name, c.name
            ORDER BY
                expired_quantity DESC
            """, countQuery = """
            SELECT COUNT(*) FROM (
                SELECT p.id
                FROM products p
                JOIN categories c ON p.cat_id = c.id
                JOIN purchase_order_items poi ON poi.prod_id = p.id
                WHERE poi.expiration_date < CURRENT_DATE
                AND p.com_id = :comId
                GROUP BY p.id, p.name, c.name
            ) AS subquery
            """, nativeQuery = true)
    Page<Map<String, Object>> findExpiredProducts(
            @Param("comId") Long comId,
            Pageable pageable);

    @Query(value = """
            SELECT
                p.id AS product_id,
                p.name AS product_name,
                c.name AS category_name,
                SUM(soi.quantity) AS returned_quantity
            FROM
                products p
            JOIN
                categories c ON p.cat_id = c.id
            JOIN
                sales_order_items soi ON soi.prod_id = p.id
            JOIN
                sales_orders so ON soi.sor_id = so.id
            WHERE
                so.status = 'DEVOLVIDO'
                AND p.com_id = :comId
            GROUP BY
                p.id, p.name, c.name
            ORDER BY
                returned_quantity DESC
            """, countQuery = """
            SELECT COUNT(*) FROM (
                SELECT
                    p.id
                FROM
                    products p
                JOIN
                    categories c ON p.cat_id = c.id
                JOIN
                    sales_order_items soi ON soi.prod_id = p.id
                JOIN
                    sales_orders so ON soi.sor_id = so.id
                WHERE
                    so.status = 'DEVOLVIDO'
                    AND p.com_id = :comId
                GROUP BY
                    p.id, p.name, c.name
            ) AS subquery
            """, nativeQuery = true)
    Page<Map<String, Object>> findReturnedProducts(@Param("comId") Long comId, Pageable pageable);
}
