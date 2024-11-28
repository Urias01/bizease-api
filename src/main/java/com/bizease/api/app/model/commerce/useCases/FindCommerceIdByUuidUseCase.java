package com.bizease.api.app.model.commerce.useCases;

import com.bizease.api.app.exceptions.NotFoundException;
import com.bizease.api.app.model.commerce.entities.Commerce;
import com.bizease.api.app.model.commerce.repository.CommerceRepository;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FindCommerceIdByUuidUseCase {

    @Autowired
    private CommerceRepository commerceRepository;

    public Long findIdByUuid(String uuid) {
        return commerceRepository.findByUuid(UUID.fromString(uuid)).map(Commerce::getId).orElseThrow(() -> {
            throw new NotFoundException("Comércio");
        });
    }
}
