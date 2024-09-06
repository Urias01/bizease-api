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
public class UpdateSuppliersUseCase {

    @Autowired
    private SuppliersRepository suppliersRepository;

    @Autowired
    private CommerceRepository commerceRepository;

    public Suppliers execute(SuppliersDTO suppliersDTO, String uuid) {
        Optional<Commerce> commerceExists = this.commerceRepository.findByUuid(suppliersDTO.getCommerceUuid());

        if (!commerceExists.isPresent()) {
            throw new NotFoundException("Comércio"); 
        }

        Optional<Suppliers> supplierExists = this.suppliersRepository.findByUuid(uuid);

        if (!supplierExists.isPresent()) {
            throw new NotFoundException("Fornecedor"); 
        }

        Suppliers suppliers = supplierExists.get();
        
        suppliers.setCnpj(suppliersDTO.getCnpj());
        suppliers.setName(suppliersDTO.getName());
        suppliers.setAddress(suppliersDTO.getAddress());
        suppliers.setAddressNumber(suppliersDTO.getAddressNumber());
        suppliers.setNeighborhood(suppliersDTO.getNeighborhood());
        suppliers.setCity(suppliersDTO.getCity());
        suppliers.setUf(suppliersDTO.getUf());
        suppliers.setPostalCode(suppliersDTO.getPostalCode());
        suppliers.setCategory(suppliersDTO.getCategory());
        suppliers.setPhoneNumber(suppliersDTO.getPhoneNumber());
        suppliers.setEmail(suppliersDTO.getEmail());

        suppliers = this.suppliersRepository.save(suppliers);

        return suppliers;
      
    }
    
}
