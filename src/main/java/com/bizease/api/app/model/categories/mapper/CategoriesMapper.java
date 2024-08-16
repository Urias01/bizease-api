package com.bizease.api.app.model.categories.mapper;

import org.springframework.stereotype.Component;

import com.bizease.api.app.model.categories.entities.Categories;
import com.bizease.api.app.model.categories.request.CategoriesRequest;
import com.bizease.api.app.model.categories.response.CategoriesResponse;
import com.bizease.api.app.model.commerce.entities.Commerce;

@Component
public class CategoriesMapper {

  public Categories toCategories(CategoriesRequest request, Commerce commerce) {
    return new Categories(
        request.getName(), request.getDescription(), commerce);
  }

  public CategoriesResponse toResponse(Categories categories) {
    return new CategoriesResponse(
        categories.getName(), categories.getDescription(), categories.getCommerce());
  }

}
