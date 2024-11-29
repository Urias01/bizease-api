package com.bizease.api.app.model.movements.useCases;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bizease.api.app.exceptions.NotFoundException;
import com.bizease.api.app.model.movements.entities.Movement;
import com.bizease.api.app.model.movements.repositories.MovementRepository;

@Service
public class GetMovementsByUuidUseCase {

  @Autowired
  private MovementRepository movementRepository;

  public Movement execute(String uuid) {
    return movementRepository.findByUuid(UUID.fromString(uuid))
        .orElseThrow(() -> new NotFoundException("Movement not found"));
  }
}
