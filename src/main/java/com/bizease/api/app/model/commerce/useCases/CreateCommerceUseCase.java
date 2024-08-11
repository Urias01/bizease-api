package com.bizease.api.app.model.commerce.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bizease.api.app.exceptions.CommerceFoundException;
import com.bizease.api.app.model.commerce.entities.Commerce;
import com.bizease.api.app.model.commerce.repository.CommerceRepository;

@Service
public class CreateCommerceUseCase {

  @Autowired
  private CommerceRepository commerceRepository;

  public Commerce execute(Commerce commerceEntity) {
    this.commerceRepository.findByCnpj(commerceEntity.getCnpj())
      .ifPresent((commerce) -> {
        throw new CommerceFoundException("CNPJ já cadastrado");
      });

      return this.commerceRepository.save(commerceEntity);
  }
  
}
