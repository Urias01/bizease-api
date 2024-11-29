package com.bizease.api.app.model.commerce.useCases;

import java.time.LocalDateTime;
import java.util.Optional;

import com.bizease.api.app.model.commerce.dto.UpdateCommerceDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bizease.api.app.exceptions.NotFoundException;
import com.bizease.api.app.model.commerce.entities.Commerce;
import com.bizease.api.app.model.commerce.repository.CommerceRepository;

@Service
public class UpdateCommerceUseCase {

  @Autowired
  private CommerceRepository commerceRepository;

  public Commerce execute(String uuid, UpdateCommerceDTO updateCommerceDTO) {
    Optional<Commerce> commerceExists = this.commerceRepository.findByUuid(uuid);

    if (commerceExists.isPresent()) {
      Commerce updatedCommerce = commerceExists.get();
      updatedCommerce.setName(updateCommerceDTO.getName());
      updatedCommerce.setPhoneNumber(updateCommerceDTO.getPhoneNumber());
      updatedCommerce.setPostalCode(updateCommerceDTO.getPostalCode());
      updatedCommerce.setAddress(updateCommerceDTO.getAddress());
      updatedCommerce.setAddressNumber(updateCommerceDTO.getAddressNumber());
      updatedCommerce.setCity(updateCommerceDTO.getCity());
      updatedCommerce.setUf(updateCommerceDTO.getUf());
      updatedCommerce.setNeighborhood(updateCommerceDTO.getNeighborhood());

      this.commerceRepository.save(updatedCommerce);
      return updatedCommerce;
    } else {
      throw new NotFoundException("Comércio");
    }
  }
}
