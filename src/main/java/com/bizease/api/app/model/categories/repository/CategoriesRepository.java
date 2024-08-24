package com.bizease.api.app.model.categories.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.bizease.api.app.model.categories.entities.Categories;

import java.util.Optional;

public interface CategoriesRepository extends JpaRepository<Categories, Long>, JpaSpecificationExecutor<Categories> {
  
  Optional<Categories> findByUuid(String uuid);
  Optional<Categories> findByNameAndCommerceId(String name, Long commerceId);

}
