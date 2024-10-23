package com.bizease.api.app.model.inventories.useCases;

import com.bizease.api.app.exceptions.NotFoundException;
import com.bizease.api.app.model.commerce.entities.Commerce;
import com.bizease.api.app.model.commerce.repository.CommerceRepository;
import com.bizease.api.app.model.inventories.entities.Inventories;
import com.bizease.api.app.model.inventories.repository.InventoriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class GetInventoriesUseCase {

    @Autowired
    private InventoriesRepository inventoriesRepository;

    @Autowired
    private CommerceRepository commerceRepository;

    public List<Inventories> getAllInventories() {
        return this.inventoriesRepository.findAll();
    }

    public Inventories getByUuid(UUID uuid) {
        return this.inventoriesRepository.findByUuid(uuid).orElseThrow(() -> {
            throw new NotFoundException("Inventário");
        });
    }

    public List<Inventories> getAllInventoriesFromCommerce(Long id) {
        Commerce commerce = findCommerce(id);
        return this.inventoriesRepository.findByCommerceId(id);
    }

    private Commerce findCommerce(Long id) {
        return commerceRepository.findById(id).orElseThrow(() -> {
            throw new NotFoundException("Comércio");
        });
    }
}
