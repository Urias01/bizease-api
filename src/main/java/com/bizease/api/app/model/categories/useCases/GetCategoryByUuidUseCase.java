package com.bizease.api.app.model.categories.useCases;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bizease.api.app.exceptions.NotFoundException;
import com.bizease.api.app.model.categories.entities.Categories;
import com.bizease.api.app.model.categories.repository.CategoriesRepository;

@Service
public class GetCategoryByUuidUseCase {

  @Autowired
  private CategoriesRepository categoriesRepository;

  public Categories execute(String uuid) {
    Optional<Categories> categoryExists = categoriesRepository.findByUuid(UUID.fromString(uuid));

    if (!categoryExists.isPresent()) {
      throw new NotFoundException("Categoria");
    }

    return categoryExists.get();
  }

}
