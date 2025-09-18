package com.bizease.api.app.model.categories.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bizease.api.app.exceptions.AlreadyExistsException;
import com.bizease.api.app.exceptions.NotFoundException;
import com.bizease.api.app.model.categories.dto.CategoriesDTO;
import com.bizease.api.app.model.categories.entities.Categories;
import com.bizease.api.app.model.categories.filter.CategoriesFilter;
import com.bizease.api.app.model.categories.useCases.CreateCategoriesUseCase;
import com.bizease.api.app.model.categories.useCases.DeleteCategoriesUseCase;
import com.bizease.api.app.model.categories.useCases.GetAllCategoriesUseCase;
import com.bizease.api.app.model.categories.useCases.GetCategoryByUuidUseCase;
import com.bizease.api.app.model.categories.useCases.UpdateCategoriesUseCase;
import com.bizease.api.app.model.commons.PageReturn;
import com.bizease.api.app.responses.ApiResponse;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/categories")
public class CategoriesController {

  @Autowired
  private CreateCategoriesUseCase createCategoriesUseCase;
  @Autowired
  private UpdateCategoriesUseCase updateCategoriesUseCase;
  @Autowired
  private DeleteCategoriesUseCase deleteCategoriesUseCase;
  @Autowired
  private GetAllCategoriesUseCase getAllCategoriesUseCase;
  @Autowired
  private GetCategoryByUuidUseCase getCategoryByUuidUseCase;

  @GetMapping("/{uuid}")
  public ResponseEntity<Categories> list(@PathVariable String uuid) {
    Categories categories = this.getCategoryByUuidUseCase.execute(uuid);
    return ResponseEntity.ok(categories);
  }

  @GetMapping
  public PageReturn<List<Map<String, Object>>> list(@ModelAttribute CategoriesFilter filter,
      HttpServletRequest request) {
    filter.setCommerceUuid((String) request.getAttribute("commerce_uuid"));
    return this.getAllCategoriesUseCase.execute(filter);
  }

  @PostMapping
  public ResponseEntity<ApiResponse<Long>> create(@RequestBody CategoriesDTO categoriesDTO,
      HttpServletRequest request) {
    String commerceUuid = (String) request.getAttribute("commerce_uuid");
    Long categoryId = this.createCategoriesUseCase.execute(categoriesDTO, commerceUuid);
    return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(categoryId, 201));
  }

  @PutMapping("/{uuid}")
  public ResponseEntity<ApiResponse<Long>> update(
      @RequestBody CategoriesDTO categoriesDTO,
      @PathVariable String uuid,
      HttpServletRequest request) {
    String commerceUuid = (String) request.getAttribute("commerce_uuid");
    categoriesDTO.setCommerceUuid(commerceUuid);
    Long categoryId = this.updateCategoriesUseCase.execute(categoriesDTO, uuid);
    return ResponseEntity.status(200).body(ApiResponse.success(categoryId, 200));
  }

  @DeleteMapping("/{uuid}")
  public ResponseEntity<ApiResponse<Void>> delete(@PathVariable String uuid) {
    this.deleteCategoriesUseCase.execute(uuid);
    return ResponseEntity.status(200).body(ApiResponse.success(null, 204));
  }
}
