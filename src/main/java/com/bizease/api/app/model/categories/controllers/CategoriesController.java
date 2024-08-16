package com.bizease.api.app.model.categories.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bizease.api.app.model.categories.request.CategoriesRequest;
import com.bizease.api.app.model.categories.response.CategoriesResponse;
import com.bizease.api.app.model.categories.service.CategoriesService;
import com.bizease.api.app.responses.ApiResponse;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/categories")
public class CategoriesController {

  @Autowired
  private CategoriesService categoriesService;

  @PostMapping("/")
  public ResponseEntity<ApiResponse<CategoriesResponse>> create(@RequestBody CategoriesRequest categoriesRequest) {
    CategoriesResponse response = this.categoriesService.create(categoriesRequest);
    ApiResponse<CategoriesResponse> apiResponse = new ApiResponse<>(true, 1, response);
    return ResponseEntity.status(201).body(apiResponse);
  }

}
