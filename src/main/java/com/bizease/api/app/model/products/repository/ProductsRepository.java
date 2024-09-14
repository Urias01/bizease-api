package com.bizease.api.app.model.products.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.bizease.api.app.model.products.entities.Products;

public interface ProductsRepository extends JpaRepository<Products, Long>, JpaSpecificationExecutor<Products> {
    
    Optional<Products> findByUuid(String uuid);
    Optional<Products> findByNameAndCommerceId(String name, Long commerceId);

}
