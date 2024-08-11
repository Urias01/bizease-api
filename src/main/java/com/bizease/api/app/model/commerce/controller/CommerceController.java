package com.bizease.api.app.model.commerce.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bizease.api.app.model.commerce.entities.Commerce;
import com.bizease.api.app.model.commerce.useCases.CreateCommerceUseCase;
import com.bizease.api.app.model.commerce.useCases.UpdateCommerceUseCase;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping(value = "/commerces")
public class CommerceController {

  @Autowired
  private CreateCommerceUseCase createCommerceUseCase;
  @Autowired
  private UpdateCommerceUseCase updateCommerceUseCase;

  @PostMapping("/")
  public ResponseEntity<Object> create(@RequestBody Commerce commerceRequest) {
    try {
      var result = this.createCommerceUseCase.execute(commerceRequest);
      return ResponseEntity.status(201).body(result);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @PutMapping("/{uuid}")
  public ResponseEntity<Object> update(@PathVariable String uuid, @RequestBody Commerce commerceEntity) {
    try {
      var result = this.updateCommerceUseCase.execute(uuid, commerceEntity);
      return ResponseEntity.ok().body(result);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }
}
