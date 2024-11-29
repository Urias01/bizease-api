package com.bizease.api.app.model.movements.repositories;

import com.bizease.api.app.model.movements.entities.Movement;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface MovementRepository extends JpaRepository<Movement, Long>, JpaSpecificationExecutor<Movement> {
    Optional<Movement> findByUuid(UUID uuid);
    List<Movement> findByCommerceId(Long id);
}
