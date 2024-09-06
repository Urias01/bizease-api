package com.bizease.api.app.model.suppliers.specification;

import org.springframework.data.jpa.domain.Specification;

import com.bizease.api.app.model.suppliers.entities.Suppliers;


public class SuppliersSpecification {
 
    
  public static Specification<Suppliers> commerceUuidEquals(String uuid) {
    if (uuid == null) {
      return null;
    }

    return (root, query, cb) -> cb.equal(root.get("commerce").get("uuid"), uuid);
  }

  public static Specification<Suppliers> nameLike(String name) {
    if (name == null) {
      return null;
    }

    return (root, query, cb) -> cb.like(root.get("name"), "%" + name + "%");
  }


}
