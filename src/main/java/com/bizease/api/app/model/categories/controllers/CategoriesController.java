package com.bizease.api.app.model.categories.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bizease.api.app.exceptions.AlreadyExistsException;
import com.bizease.api.app.model.categories.dto.CategoriesDTO;
import com.bizease.api.app.model.categories.entities.Categories;
import com.bizease.api.app.model.categories.useCases.CreateCategoriesUseCase;
import com.bizease.api.app.responses.ApiResponse;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/categories")
public class CategoriesController {

  @Autowired
  private CreateCategoriesUseCase createCategoriesUseCase;

  @PostMapping
  public ResponseEntity<Object> create(@RequestBody CategoriesDTO categoriesDTO) {
    try {
      Categories response = this.createCategoriesUseCase.execute(categoriesDTO);
      ApiResponse<Categories> apiResponse = new ApiResponse<>(true, 1, response);
      return ResponseEntity.status(201).body(apiResponse);
    } catch (AlreadyExistsException e) {
      ApiResponse<String> apiResponse = new ApiResponse<>(false, 0, e.getMessage());
      return ResponseEntity.status(422).body(apiResponse);
    } catch (Exception e) {
      ApiResponse<String> apiResponse = new ApiResponse<>(false, 0, e.getMessage());
      return ResponseEntity.status(500).body(apiResponse);
    }
  }
}
