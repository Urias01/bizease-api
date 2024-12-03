package com.bizease.api.app.model.purchase_orders.specification;

import org.springframework.data.jpa.domain.Specification;

import com.bizease.api.app.model.purchase_orders.entities.PurchaseOrders;

public class PurcharseOrdersSpecification {
  public static Specification<PurchaseOrders> idEquals(Long id) {
    if (id == null) {
      return null;
    }

    return (root, query, cb) -> cb.equal(root.get("id"), id);
  }

  public static Specification<PurchaseOrders> commerceUuidEquals(String uuid) {
    if (uuid.isEmpty()) {
      return null;
    }

    return (root, query, cb) -> cb.equal(root.get("commerce").get("uuid"), uuid);
  }

  public static Specification<PurchaseOrders> statusEquals(String status) {
    if (status == null) {
      return null;
    }

    return (root, query, cb) -> cb.equal(root.get("status"), status);
  }
}
