package com.bizease.api.app.model.commerce.useCases;

import com.bizease.api.app.model.commons.enums.IsActiveEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bizease.api.app.exceptions.AlreadyExistsException;
import com.bizease.api.app.model.commerce.entities.Commerce;
import com.bizease.api.app.model.commerce.repository.CommerceRepository;

@Service
public class CreateCommerceUseCase {

  @Autowired
  private CommerceRepository commerceRepository;

  public Long execute(Commerce commerceEntity) {
    this.commerceRepository.findByCnpj(commerceEntity.getCnpj())
      .ifPresent((commerce) -> {
        throw new AlreadyExistsException("CNPJ");
      });

      commerceEntity.setIsActive(IsActiveEnum.ACTIVE);
      
      Commerce commerce = this.commerceRepository.save(commerceEntity);
      
      return commerce.getId();
  }
  
}
