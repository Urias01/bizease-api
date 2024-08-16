package com.bizease.api.app.model.categories.service;

import com.bizease.api.app.model.categories.request.CategoriesRequest;
import com.bizease.api.app.model.categories.response.CategoriesResponse;

public interface CategoriesService {

   public CategoriesResponse create(CategoriesRequest categoriesRequest);
  
} 
