package com.bizease.api.app.model.commerce.useCases;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bizease.api.app.exceptions.NotFoundException;
import com.bizease.api.app.model.commerce.entities.Commerce;
import com.bizease.api.app.model.commerce.repository.CommerceRepository;

@Service
public class UpdateCommerceUseCase {

  @Autowired
  private CommerceRepository commerceRepository;

  public Commerce execute(String uuid, Commerce commerceEntity) {
     Optional<Commerce> commerceExists = this.commerceRepository.findByUuid(uuid);

    if (!commerceExists.isPresent()) {
      throw new NotFoundException("Comércio");
    }

    Commerce model = commerceExists.get();

    model.setName(commerceEntity.getName());
    model.setCnpj(commerceEntity.getCnpj());
    model.setPostalCode(commerceEntity.getPostalCode());
    model.setAddress(commerceEntity.getAddress());
    model.setAddressNumber(commerceEntity.getAddressNumber());
    model.setNeighborhood(commerceEntity.getNeighborhood());
    model.setCity(commerceEntity.getCity());
    model.setUf(commerceEntity.getUf());
    model.setUpdatedAt(LocalDateTime.now());


    return this.commerceRepository.save(model);
  }
  
}
