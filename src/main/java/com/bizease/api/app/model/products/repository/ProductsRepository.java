package com.bizease.api.app.model.products.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bizease.api.app.model.products.entities.Products;

public interface ProductsRepository extends JpaRepository<Products, Long> {
    
    Optional<Products> findByUuid(String uuid);

}
