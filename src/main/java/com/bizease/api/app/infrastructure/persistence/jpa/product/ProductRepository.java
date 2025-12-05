package com.bizease.api.app.infrastructure.persistence.jpa.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bizease.api.app.models.entities.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {

  Product findByNameAndTenantId(String name, String tenantId);
}
