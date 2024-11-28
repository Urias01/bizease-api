package com.bizease.api.app.model.categories.useCases;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bizease.api.app.exceptions.AlreadyExistsException;
import com.bizease.api.app.exceptions.NotFoundException;
import com.bizease.api.app.model.categories.dto.CategoriesDTO;
import com.bizease.api.app.model.categories.entities.Categories;
import com.bizease.api.app.model.categories.repository.CategoriesRepository;
import com.bizease.api.app.model.commerce.entities.Commerce;
import com.bizease.api.app.model.commerce.repository.CommerceRepository;
import com.bizease.api.app.model.commons.enums.IsActiveEnum;

@Service
public class UpdateCategoriesUseCase {

  @Autowired
  private CategoriesRepository categoriesRepository;
  @Autowired
  private CommerceRepository commerceRepository;

  public Categories execute(CategoriesDTO categoriesDTO, String uuid) {

    Optional<Commerce> commerceExists = this.commerceRepository.findByUuid(categoriesDTO.getCommerceUuid());

    if (!commerceExists.isPresent()) {
      throw new NotFoundException("Comércio");
    }

    Optional<Categories> categoriesExists = this.categoriesRepository.findByUuid(UUID.fromString(uuid));

    if (!categoriesExists.isPresent()) {
      throw new NotFoundException("Categoria");
    }

    Commerce commerce = commerceExists.get();

    Categories model = categoriesExists.get();

    this.categoriesRepository.findByNameAndCommerceId(
        categoriesDTO.getName(),
        commerce.getId()).ifPresent((category) -> {
          throw new AlreadyExistsException("Categoria");
        });

    model.setName(categoriesDTO.getName());
    model.setDescription(categoriesDTO.getDescription());
    if (categoriesDTO.getIsActive() != null) {
      model.setIsActive(IsActiveEnum.from(categoriesDTO.getIsActive()));
    }

    model = this.categoriesRepository.save(model);

    return model;
  }

}
