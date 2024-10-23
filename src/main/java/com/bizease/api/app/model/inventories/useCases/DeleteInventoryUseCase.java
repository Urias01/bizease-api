package com.bizease.api.app.model.inventories.useCases;

import com.bizease.api.app.exceptions.NotFoundException;
import com.bizease.api.app.model.inventories.entities.Inventories;
import com.bizease.api.app.model.inventories.repository.InventoriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class DeleteInventoryUseCase {

    @Autowired
    private InventoriesRepository inventoriesRepository;

    public void execute(UUID uuid) {
        Optional<Inventories> verifyInventory = inventoriesRepository.findByUuid(uuid);

        if (verifyInventory.isPresent()) {
            Inventories inventory = verifyInventory.get();
            inventoriesRepository.delete(inventory);
        } else {
            throw new NotFoundException("Inventário");
        }
    }
}
