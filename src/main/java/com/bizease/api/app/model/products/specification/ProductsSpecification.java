package com.bizease.api.app.model.products.specification;

import org.springframework.data.jpa.domain.Specification;

import com.bizease.api.app.model.products.entities.Products;

public class ProductsSpecification {

    public static Specification<Products> commerceUuidEquals(String uuid) {
        if (uuid.isEmpty()) {
            return null;
        }
        
        return (root, query, cb) -> cb.equal(root.get("commerce").get("uuid"), uuid);
    }

    public static Specification<Products> nameLike(String name) {
        if (name.isEmpty()) {
            return null;
        }
        
        return (root, query, cb) -> cb.like(root.get("name"), "%" + name + "%");
    }

}
