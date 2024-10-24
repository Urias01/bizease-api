package com.bizease.api.app.model.movements.repositories;

import com.bizease.api.app.model.movements.entities.Movement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovementRepository extends JpaRepository<Movement, Long> {
}
