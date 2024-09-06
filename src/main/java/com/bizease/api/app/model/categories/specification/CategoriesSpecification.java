package com.bizease.api.app.model.categories.specification;

import org.springframework.data.jpa.domain.Specification;

import com.bizease.api.app.model.categories.entities.Categories;

public class CategoriesSpecification {

  public static Specification<Categories> commerceUuidEquals(String uuid) {
    if (uuid == null) {
      return null;
    }

    return (root, query, cb) -> cb.equal(root.get("commerce").get("uuid"), uuid);
  }

  public static Specification<Categories> nameLike(String name) {
    if (name == null) {
      return null;
    }

    return (root, query, cb) -> cb.like(root.get("name"), "%" + name + "%");
  }

}
