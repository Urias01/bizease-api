package com.bizease.api.app.model.products.useCases;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bizease.api.app.exceptions.NotFoundException;
import com.bizease.api.app.model.products.entities.Products;
import com.bizease.api.app.model.products.repository.ProductsRepository;

@Service
public class GetProductByUuidUseCase {

  @Autowired
  private ProductsRepository productsRepository;

  public Products execute(String uuid) {

    Optional<Products> productsExists = productsRepository.findByUuid(uuid);

    if (!productsExists.isPresent()) {
      throw new NotFoundException("Produto");
    }

    return productsExists.get();

  }
}
