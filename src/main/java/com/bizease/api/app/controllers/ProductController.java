package com.bizease.api.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bizease.api.app.models.request.ProductRequest;
import com.bizease.api.app.models.response.ApiResponse;
import com.bizease.api.app.models.response.CreateResponse;
import com.bizease.api.app.useCases.products.CreateProduct;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/products")
public class ProductController {

  @Autowired
  private CreateProduct createProduct;

  @PostMapping
  public ResponseEntity<ApiResponse<CreateResponse>> create(@RequestBody @Valid ProductRequest request) {
    CreateResponse productUuid = createProduct.execute(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(productUuid));
  }
}
