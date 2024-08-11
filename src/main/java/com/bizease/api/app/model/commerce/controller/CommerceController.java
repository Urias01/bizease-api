package com.bizease.api.app.model.commerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bizease.api.app.model.commerce.entities.Commerce;
import com.bizease.api.app.model.commerce.useCases.CreateCommerceUseCase;

@RestController
@RequestMapping(value = "/commerces")
public class CommerceController {

  @Autowired
  private CreateCommerceUseCase createCommerceUseCase;

  @PostMapping("/")
  public ResponseEntity<Object> create(@RequestBody Commerce commerceRequest) {
    try {
      var result = this.createCommerceUseCase.execute(commerceRequest);
      return ResponseEntity.ok().body(result);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }
}
