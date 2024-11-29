package com.bizease.api.app.model.commerce.controller;

import com.bizease.api.app.model.commerce.dto.UpdateCommerceDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bizease.api.app.model.commerce.entities.Commerce;
import com.bizease.api.app.model.commerce.useCases.CreateCommerceUseCase;
import com.bizease.api.app.model.commerce.useCases.FindMyCommerceUseCase;
import com.bizease.api.app.model.commerce.useCases.ActiveDeactiveCommerceUseCase;
import com.bizease.api.app.model.commerce.useCases.UpdateCommerceUseCase;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;



@RestController
@RequestMapping(value = "/commerces")
public class CommerceController {

  @Autowired
  private CreateCommerceUseCase createCommerceUseCase;
  @Autowired
  private UpdateCommerceUseCase updateCommerceUseCase;
  @Autowired 
  private ActiveDeactiveCommerceUseCase activeDeactiveCommerceUseCase;
  @Autowired
  private FindMyCommerceUseCase findMyCommerceUseCase;

  @GetMapping("/details")
  public  ResponseEntity<Object> getMyCommerce(HttpServletRequest request) {
    try {
      String commerceUuid = (String) request.getAttribute("commerce_uuid");
      var result = this.findMyCommerceUseCase.execute(commerceUuid);
      return ResponseEntity.ok().body(result);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }
  
  @PostMapping("/")
  public ResponseEntity<Object> create(@RequestBody Commerce commerceRequest) {
    try {
      var result = this.createCommerceUseCase.execute(commerceRequest);
      return ResponseEntity.status(201).body(result);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @PutMapping("/update")
  public ResponseEntity<Object> update(@RequestBody UpdateCommerceDTO updateCommerceDTO, HttpServletRequest request) {
    try {
      String commerceUuid = (String) request.getAttribute("commerce_uuid");
      var result = this.updateCommerceUseCase.execute(commerceUuid, updateCommerceDTO);
      return ResponseEntity.ok().body(result);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @PatchMapping("/active-deactive")
  public ResponseEntity<Object> activeDeactive(HttpServletRequest request) {
    try {
      String commerceUuid = (String) request.getAttribute("commerce_uuid");
      String response = this.activeDeactiveCommerceUseCase.execute(commerceUuid);
      return ResponseEntity.ok().body(response);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }
}
