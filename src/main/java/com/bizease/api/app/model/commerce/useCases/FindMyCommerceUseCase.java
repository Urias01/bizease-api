package com.bizease.api.app.model.commerce.useCases;

import java.util.Optional;

import com.bizease.api.app.model.commons.enums.IsActiveEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bizease.api.app.exceptions.NotActiveCommerceException;
import com.bizease.api.app.exceptions.NotFoundException;
import com.bizease.api.app.model.commerce.entities.Commerce;
import com.bizease.api.app.model.commerce.repository.CommerceRepository;

@Service
public class FindMyCommerceUseCase {

  @Autowired
  private CommerceRepository commerceRepository;

  public Commerce execute(String uuid) {

    Optional<Commerce> commerceExists = this.commerceRepository.findByUuid(uuid);
    
    if (!commerceExists.isPresent()) {
      throw new NotFoundException("Comércio");
    }

    Commerce commerce = commerceExists.get();
    
    if (commerce.getIsActive() == IsActiveEnum.INACTIVE) {
      throw new NotActiveCommerceException();
    }

    return commerce;

  }
}
