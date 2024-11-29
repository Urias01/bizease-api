package com.bizease.api.app.model.suppliers.useCases;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bizease.api.app.exceptions.NotFoundException;
import com.bizease.api.app.model.suppliers.entities.Suppliers;
import com.bizease.api.app.model.suppliers.repository.SuppliersRepository;

@Service
public class GetSupplierByUuidUseCase {

  @Autowired
  private SuppliersRepository suppliersRepository;

  public Suppliers execute(String uuid) {

    Optional<Suppliers> supplierExists = this.suppliersRepository.findByUuid(UUID.fromString(uuid));

    if (!supplierExists.isPresent()) {
      throw new NotFoundException("Fornecedor");
    }

    return supplierExists.get();
  }
}