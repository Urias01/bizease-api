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

    model = this.categoriesRepository.save(model);

    CategoriesResponse response = this.categoriesMapper.toResponse(model);

    return response;
  }

  @Override
  public CategoriesResponse update(CategoriesRequest categoriesRequest, String uuid) {
    Optional<Categories> categoriesExits = this.categoriesRepository.findByUuid(uuid);

    if (!categoriesExits.isPresent()) {
      throw new NotFoundException("Categoria");
    }

    Categories model = categoriesExits.get();

    model.setName(categoriesRequest.getName());
    model.setDescription(categoriesRequest.getDescription());

    this.categoriesRepository.findByNameAndCommerceId(
      model.getName(),
      model.getCommerce().getId()
    ).ifPresent((category) -> {
      System.out.println(category);
      throw new AlreadyExistsException("Categoria");
    });
    
    model = this.categoriesRepository.save(model);

    CategoriesResponse response = this.categoriesMapper.toResponse(model);

    return response;
  }
  
}
