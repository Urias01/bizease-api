package com.bizease.api.app.model.categories.useCases;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bizease.api.app.exceptions.AlreadyExistsException;
import com.bizease.api.app.exceptions.NotFoundException;
import com.bizease.api.app.model.categories.dto.CategoriesDTO;
import com.bizease.api.app.model.categories.entities.Categories;
import com.bizease.api.app.model.categories.repository.CategoriesRepository;
import com.bizease.api.app.model.commerce.entities.Commerce;
import com.bizease.api.app.model.commerce.repository.CommerceRepository;

@Service
public class CreateCategoriesUseCase {

  @Autowired
  private CategoriesRepository categoriesRepository;
  @Autowired
  private CommerceRepository commerceRepository;

  public Long execute(CategoriesDTO categoriesDTO, String uuid) {

    Optional<Commerce> commerceExists = this.commerceRepository.findByUuid(uuid);

    if (!commerceExists.isPresent()) {
      throw new NotFoundException("Comércio");
    }

    Commerce commerce = commerceExists.get();

    Categories model = new Categories(categoriesDTO, commerce);

    this.categoriesRepository.findByNameAndCommerceId(
        model.getName(),
        model.getCommerce().getId()).ifPresent((category) -> {
          throw new AlreadyExistsException("Categoria");
        });

    model = this.categoriesRepository.save(model);

    return model.getId();
  }

}
