package com.bizease.api.app.model.commerce.useCases;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bizease.api.app.exceptions.NotFoundException;
import com.bizease.api.app.model.commerce.entities.Commerce;
import com.bizease.api.app.model.commerce.repository.CommerceRepository;
import com.bizease.api.app.model.commons.enums.IsActiveEnum;

@Service
public class ActiveDeactiveCommerceUseCase {
  
  @Autowired 
  private CommerceRepository commerceRepository;

  public String execute(String uuid) {
    Optional<Commerce> commerceExists = this.commerceRepository.findByUuid(UUID.fromString(uuid));

    if (!commerceExists.isPresent()) {
      throw new NotFoundException("Comércio");
    }

    Commerce commerce = commerceExists.get();

    commerce.setIsActive(IsActiveEnum.INACTIVE);

    
    this.commerceRepository.save(commerce);

    if (commerce.getIsActive() == IsActiveEnum.ACTIVE) {
      return "Comércio ativado com sucesso!";
    }
    return "Comércio desativado com sucesso!";
  }
}
