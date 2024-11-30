package com.bizease.api.app.model.user.specification;

import org.springframework.data.jpa.domain.Specification;

import com.bizease.api.app.model.commons.enums.IsActiveEnum;
import com.bizease.api.app.model.user.entities.User;

public class UserSpecification {

  public static Specification<User> idEquals(Long id) {
    if (id == null) {
      return null;
    }

    return (root, query, cb) -> cb.equal(root.get("id"), id);
  }

  public static Specification<User> commerceUuidEquals(String uuid) {
    if (uuid == null) {
      return null;
    }

    return (root, query, cb) -> cb.equal(root.get("commerce").get("uuid"), uuid);
  }

  public static Specification<User> nameLike(String name) {
    if (name == null || name.isEmpty()) {
      return null;
    }

    return (root, query, cb) -> cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
  }

  public static Specification<User> isActive(String isActive) {
    if (isActive == null) {
      return null;
    }

    return (root, query, cb) -> cb.equal(root.get("isActive"), IsActiveEnum.valueOf(isActive));
  }

}
