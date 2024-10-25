package com.bizease.api.app.model.movements.useCases;

import com.bizease.api.app.exceptions.NotFoundException;
import com.bizease.api.app.model.inventories.entities.Inventories;
import com.bizease.api.app.model.inventories.repository.InventoriesRepository;
import com.bizease.api.app.model.movements.entities.Movement;
import com.bizease.api.app.model.movements.repositories.MovementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class GetMovementsUseCase {

    @Autowired
    private MovementRepository movementRepository;

    @Autowired
    private InventoriesRepository inventoriesRepository;

    public List<Movement> getAllMovements() {
        return this.movementRepository.findAll().stream().toList();
    }

    public Movement getByUuid(UUID uuid) {
        return this.movementRepository.findByUuid(uuid).orElseThrow(() -> {
            throw new NotFoundException("Movimentação");
        });
    }

    public List<Movement> getAllMovementsFromInventory(Long id) {
        return this.movementRepository.findByInventoryId(id).stream().toList();
    }

    private Inventories findInventory(Long id) {
        return this.inventoriesRepository.findById(id).orElseThrow(() -> {
            throw new NotFoundException("Inventário");
        });
    }
}
