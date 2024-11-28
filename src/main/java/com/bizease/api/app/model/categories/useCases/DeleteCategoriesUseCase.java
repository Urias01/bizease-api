package com.bizease.api.app.model.categories.useCases;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bizease.api.app.exceptions.NotFoundException;
import com.bizease.api.app.model.categories.entities.Categories;
import com.bizease.api.app.model.categories.repository.CategoriesRepository;

@Service
public class DeleteCategoriesUseCase {

  @Autowired
  private CategoriesRepository categoriesRepository;

  public void execute(String uuid) {
    Optional<Categories> categoriesExists = categoriesRepository.findByUuid(UUID.fromString(uuid));

    if (!categoriesExists.isPresent()) {
      throw new NotFoundException("Categorias");
    }

    Categories categories = categoriesExists.get();
    
    this.categoriesRepository.delete(categories);
  }
  
}
