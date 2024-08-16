package com.bizease.api.app.model.categories.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bizease.api.app.exceptions.AlreadyExistsException;
import com.bizease.api.app.exceptions.NotFoundException;
import com.bizease.api.app.model.categories.entities.Categories;
import com.bizease.api.app.model.categories.mapper.CategoriesMapper;
import com.bizease.api.app.model.categories.repository.CategoriesRepository;
import com.bizease.api.app.model.categories.request.CategoriesRequest;
import com.bizease.api.app.model.categories.response.CategoriesResponse;
import com.bizease.api.app.model.commerce.entities.Commerce;
import com.bizease.api.app.model.commerce.repository.CommerceRepository;

import java.util.Optional;

@Service
public class CategoriesServiceImpl implements CategoriesService {

  
  @Autowired
  private CategoriesRepository categoriesRepository;
  @Autowired
  private CommerceRepository commerceRepository;
  @Autowired
  private CategoriesMapper categoriesMapper;

  @Override
  public CategoriesResponse create(CategoriesRequest categoriesRequest) {
    
    Optional<Commerce> commerceExists = this.commerceRepository.findById(categoriesRequest.getCommerceId());

    if (!commerceExists.isPresent()) {
      throw new NotFoundException("Comércio");
    }
    
    Commerce commerce = commerceExists.get();

    Categories model = this.categoriesMapper.toCategories(categoriesRequest, commerce);
    
    this.categoriesRepository.findByNameAndCommerceId(
      model.getName(),
      model.getCommerce().getId()
    ).ifPresent((category) -> {
      throw new AlreadyExistsException("Categoria");
    });

    Categories categories = new Categories(model);

    this.categoriesRepository.save(categories);

    CategoriesResponse response = this.categoriesMapper.toResponse(categories);

    return response;
  }
  
}
