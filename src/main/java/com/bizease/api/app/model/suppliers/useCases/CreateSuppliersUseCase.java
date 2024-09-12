package com.bizease.api.app.model.suppliers.useCases;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bizease.api.app.exceptions.NotFoundException;
import com.bizease.api.app.model.commerce.entities.Commerce;
import com.bizease.api.app.model.commerce.repository.CommerceRepository;
import com.bizease.api.app.model.suppliers.dto.SuppliersDTO;
import com.bizease.api.app.model.suppliers.entities.Suppliers;
import com.bizease.api.app.model.suppliers.repository.SuppliersRepository;

@Service

public class CreateSuppliersUseCase {
    
    @Autowired
    private SuppliersRepository suppliersRepository;
    @Autowired
    private CommerceRepository commerceRepository;

    public Suppliers execute(SuppliersDTO suppliersDTO) {
        Optional<Commerce> commerceExists = this.commerceRepository.findByUuid(suppliersDTO.getCommerceUuid());

        if (!commerceExists.isPresent()) {
            throw new NotFoundException("Comércio");
        }

        Suppliers suppliers = new Suppliers(suppliersDTO, commerceExists.get());
        this.suppliersRepository.save(suppliers);

        return suppliers;
    }

}
