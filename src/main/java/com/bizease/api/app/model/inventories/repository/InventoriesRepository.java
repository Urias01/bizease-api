package com.bizease.api.app.model.inventories.repository;

import com.bizease.api.app.model.inventories.entities.Inventories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface InventoriesRepository extends JpaRepository<Inventories, Long> {
    Optional<Inventories> findByUuid(UUID uuid);
    List<Inventories> findByCommerceId(Long commerceId);
}
