package com.bizease.api.app.model.suppliers.useCases;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bizease.api.app.exceptions.NotFoundException;
import com.bizease.api.app.model.suppliers.entities.Suppliers;
import com.bizease.api.app.model.suppliers.repository.SuppliersRepository;

@Service
public class DeleteSuppliersUseCase {

    @Autowired
    private SuppliersRepository suppliersRepository;

    public void execute(String uuid) {
        Optional<Suppliers> supplierExists = suppliersRepository.findByUuid(uuid);

        if (!supplierExists.isPresent()) {
            throw new NotFoundException("Fornecedor");
        }
        
        Suppliers suppliers = supplierExists.get();

        this.suppliersRepository.delete(suppliers);
    }
    
}
