package com.bizease.api.app.model.sales_orders.specifications;

import org.springframework.data.jpa.domain.Specification;

import com.bizease.api.app.model.sales_orders.entities.SalesOrders;

public class SalesOrderSpecification {

  public static Specification<SalesOrders> idEquals(Long id) {
    if (id == null) {
      return null;
    }

    return (root, query, cb) -> cb.equal(root.get("id"), id);
  }

  public static Specification<SalesOrders> commerceUuidEquals(String uuid) {
    if (uuid.isEmpty()) {
      return null;
    }

    return (root, query, cb) -> cb.equal(root.get("commerce").get("uuid"), uuid);
  }

}
