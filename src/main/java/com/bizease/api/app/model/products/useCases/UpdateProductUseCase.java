package com.bizease.api.app.model.products.useCases;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bizease.api.app.exceptions.NotFoundException;
import com.bizease.api.app.model.categories.entities.Categories;
import com.bizease.api.app.model.categories.repository.CategoriesRepository;
import com.bizease.api.app.model.commerce.entities.Commerce;
import com.bizease.api.app.model.commerce.repository.CommerceRepository;
import com.bizease.api.app.model.commons.enums.IsActiveEnum;
import com.bizease.api.app.model.products.dto.ProductsDTO;
import com.bizease.api.app.model.products.entities.Products;
import com.bizease.api.app.model.products.repository.ProductsRepository;

@Service
public class UpdateProductUseCase {

  @Autowired
  ProductsRepository productsRepository;

  @Autowired
  CommerceRepository commerceRepository;

  @Autowired
  CategoriesRepository categoriesRepository;

  public Products execute(String uuid, ProductsDTO productsDTO) {

    Optional<Commerce> commerceExists = this.commerceRepository.findByUuid(productsDTO.getCommerceUuid());

    if (!commerceExists.isPresent()) {
      throw new NotFoundException("Comércio");
    }

    Optional<Categories> categoryExists = this.categoriesRepository
        .findByUuid(UUID.fromString(productsDTO.getCategoryUuid()));

    if (!categoryExists.isPresent()) {
      throw new NotFoundException("Categoria");
    }

    Optional<Products> productExists = this.productsRepository.findByUuid(uuid);

    if (!productExists.isPresent()) {
      throw new NotFoundException("Produto");
    }

    Categories categories = categoryExists.get();
    Products product = productExists.get();

    product.setName(productsDTO.getName());
    product.setUnit(productsDTO.getUnit());
    product.setMinimumStock(productsDTO.getMinimumStock());
    product.setLocation(productsDTO.getLocation());
    product.setDescription(productsDTO.getDescription());
    product.setCategories(categories);

    product = this.productsRepository.save(product);

    return product;

  }

}
