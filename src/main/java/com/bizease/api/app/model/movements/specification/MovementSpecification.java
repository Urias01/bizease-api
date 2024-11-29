package com.bizease.api.app.model.movements.specification;

import org.springframework.data.jpa.domain.Specification;

import com.bizease.api.app.model.movements.enums.TypeEnum;
import com.bizease.api.app.model.movements.entities.Movement;

public class MovementSpecification {

  public static Specification<Movement> idEquals(Long id) {
    if (id == null) {
      return null;
    }

    return (root, query, cb) -> cb.equal(root.get("id"), id);
  }

  public static Specification<Movement> commerceUuidEquals(String uuid) {
    if (uuid.isEmpty()) {
      return null;
    }

    return (root, query, cb) -> cb.equal(root.get("commerce").get("uuid"), uuid);
  }

  public static Specification<Movement> originLike(String origin) {
    if (origin == null || origin.isEmpty()) {
      return null;
    }

    return (root, query, cb) -> cb.like(cb.lower(root.get("origin")), "%" + origin.toLowerCase() + "%");
  }

  public static Specification<Movement> typeEnumEquals(String type) {
    if (type == null) {
      return null;
    }

    return (root, query, cb) -> cb.equal(root.get("type"), TypeEnum.valueOf(type));
  }

}
