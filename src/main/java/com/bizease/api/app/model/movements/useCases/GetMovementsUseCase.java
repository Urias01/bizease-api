package com.bizease.api.app.model.movements.useCases;

import com.bizease.api.app.exceptions.NotFoundException;
import com.bizease.api.app.model.commerce.entities.Commerce;
import com.bizease.api.app.model.commerce.repository.CommerceRepository;
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
    private CommerceRepository commerceRepository;

    public List<Movement> getAllMovements() {
        return this.movementRepository.findAll().stream().toList();
    }

    public Movement getByUuid(UUID uuid) {
        return this.movementRepository.findByUuid(uuid).orElseThrow(() -> {
            throw new NotFoundException("Movimentação");
        });
    }

    public List<Movement> getAllMovementsFromCommerce(Long id) {
        return this.movementRepository.findByCommerceId(id).stream().toList();
    }

    private Commerce findCommerce(Long id) {
        return this.commerceRepository.findById(id).orElseThrow(() -> {
            throw new NotFoundException("Comércio");
        });
    }
}
