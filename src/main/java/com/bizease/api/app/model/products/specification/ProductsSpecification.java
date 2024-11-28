package com.bizease.api.app.model.products.specification;

import org.springframework.data.jpa.domain.Specification;

import com.bizease.api.app.model.commons.enums.IsActiveEnum;
import com.bizease.api.app.model.products.entities.Products;

public class ProductsSpecification {

    public static Specification<Products> idEquals(Long id) {
        if (id == null) {
            return null;
        }

        return (root, query, cb) -> cb.equal(root.get("id"), id);
    }

    public static Specification<Products> commerceUuidEquals(String uuid) {
        if (uuid.isEmpty()) {
            return null;
        }

        return (root, query, cb) -> cb.equal(root.get("commerce").get("uuid"), uuid);
    }

    public static Specification<Products> nameLike(String name) {
        if (name == null || name.isEmpty()) {
            return null;
        }

        return (root, query, cb) -> cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }

    public static Specification<Products> orderByStatus() {
        return (root, query, cb) -> {
            query.orderBy(cb.asc(root.get("isActive")));
            return cb.conjunction();
        };
    }

    public static Specification<Products> isActive(String isActive) {
        if (isActive == null) {
            return null;
        }

        return (root, query, cb) -> cb.equal(root.get("isActive"), IsActiveEnum.valueOf(isActive));
    }

}
