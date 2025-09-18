package com.bizease.api.app.model.commerce.controller;

import com.bizease.api.app.model.commerce.dto.UpdateCommerceDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import com.bizease.api.app.responses.ApiResponse;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
  public  ResponseEntity<ApiResponse<Object>> getMyCommerce(HttpServletRequest request) {
      String commerceUuid = (String) request.getAttribute("commerce_uuid");
      var result = this.findMyCommerceUseCase.execute(commerceUuid);
      return ResponseEntity.ok().body(ApiResponse.success(result, 200));
  }
  
  @PostMapping("/")
  public ResponseEntity<ApiResponse<Long>> create(@RequestBody Commerce commerceRequest) {
      Long commerceId = this.createCommerceUseCase.execute(commerceRequest);
      return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(commerceId, 201));
  }

  @PutMapping("/update")
  public ResponseEntity<ApiResponse<Long>> update(@RequestBody UpdateCommerceDTO updateCommerceDTO, HttpServletRequest request) {
      String commerceUuid = (String) request.getAttribute("commerce_uuid");
      Long commerceId = this.updateCommerceUseCase.execute(commerceUuid, updateCommerceDTO);
      return ResponseEntity.ok().body(ApiResponse.success(commerceId,200));
  }

  @PatchMapping("/active-deactive")
  public ResponseEntity<ApiResponse<String>> activeDeactive(HttpServletRequest request) {
      String commerceUuid = (String) request.getAttribute("commerce_uuid");
      String response = this.activeDeactiveCommerceUseCase.execute(commerceUuid);
      return ResponseEntity.ok().body(ApiResponse.success(response,200));
  }
}
