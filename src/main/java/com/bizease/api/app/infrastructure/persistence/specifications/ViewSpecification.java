package com.bizease.api.app.infrastructure.persistence.specifications;

import org.springframework.data.jpa.domain.Specification;

import com.bizease.api.app.models.entities.View;
import com.bizease.api.app.models.enums.ActivationsState;

public class ViewSpecification {
  
  
    public static Specification<View> idEquals(Long id) {
        if (id == null) {
            return null;
        }

        return (root, query, cb) -> cb.equal(root.get("id"), id);
    }

    public static Specification<View> tenantUuidEquals(String uuid) {
        if (uuid.isEmpty()) {
            return null;
        }

        return (root, query, cb) -> cb.equal(root.get("tenant").get("id"), uuid);
    }

    public static Specification<View> nameLike(String name) {
        if (name == null || name.isEmpty()) {
            return null;
        }

        return (root, query, cb) -> cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }

    public static Specification<View> isActive(String status) {
        if (status == null) {
            return null;
        }

        return (root, query, cb) -> cb.equal(root.get("status"), ActivationsState.valueOf(status));
    }

}
