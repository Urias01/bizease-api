package com.bizease.api.app.model.inventories.useCases;

import com.bizease.api.app.exceptions.NotFoundException;
import com.bizease.api.app.model.commerce.entities.Commerce;
import com.bizease.api.app.model.commerce.repository.CommerceRepository;
import com.bizease.api.app.model.inventories.dto.InventoriesDTO;
import com.bizease.api.app.model.inventories.entities.Inventories;
import com.bizease.api.app.model.inventories.repository.InventoriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateInventoryUseCase {

    @Autowired
    private InventoriesRepository inventoriesRepository;

    @Autowired
    private CommerceRepository commerceRepository;

    public Inventories execute(InventoriesDTO inventoriesDTO) {
        var commerce = findCommerce(inventoriesDTO.getCommerceId());

        Inventories inventories = new Inventories();
        inventories.setQuantity(inventories.getQuantity());
        inventories.setLocation(inventories.getLocation());
        inventories.setCommerce(commerce);

        return inventoriesRepository.save(inventories);
    }

    private Commerce findCommerce(Long id) {
        return this.commerceRepository.findById(id).orElseThrow(() -> {
            throw new NotFoundException("Comércio");
        });
    }
}
