package com.bizease.api.app.model.inventories.useCases;

import com.bizease.api.app.exceptions.NotFoundException;
import com.bizease.api.app.model.commerce.entities.Commerce;
import com.bizease.api.app.model.commerce.repository.CommerceRepository;
import com.bizease.api.app.model.inventories.dto.InventoriesDTO;
import com.bizease.api.app.model.inventories.entities.Inventories;
import com.bizease.api.app.model.inventories.repository.InventoriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UpdateInventoryUseCase {

    @Autowired
    private InventoriesRepository inventoriesRepository;

    @Autowired
    private CommerceRepository commerceRepository;

    public Inventories execute(UUID uuid, InventoriesDTO inventoriesDTO) {
        Inventories updatedInventory = this.inventoriesRepository.findByUuid(uuid).orElseThrow(() -> {
            throw new NotFoundException("Inventário");
        });

        var commerce = findCommerce(inventoriesDTO.getCommerceId());

        updatedInventory.setQuantity(inventoriesDTO.getQuantity());
        updatedInventory.setLocation(inventoriesDTO.getLocation());
        updatedInventory.setCommerce(commerce);

        return inventoriesRepository.save(updatedInventory);
    }

    private Commerce findCommerce(Long id) {
        return this.commerceRepository.findById(id).orElseThrow(() -> {
            throw new NotFoundException("Comércio");
        });
    }
}
