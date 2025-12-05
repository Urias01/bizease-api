package com.bizease.api.app.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bizease.api.app.models.request.TenantRequest;
import com.bizease.api.app.models.response.ApiResponse;
import com.bizease.api.app.models.response.CreateResponse;
import com.bizease.api.app.services.tenants.CreateTenant;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/tenants")
public class TenantController {

  private final CreateTenant createTenant;

  public TenantController(CreateTenant createTenant) {
    this.createTenant = createTenant;
  }

  @PostMapping
  public ResponseEntity<ApiResponse<CreateResponse>> create(@Valid @RequestBody TenantRequest request) {
    CreateResponse tenantId = createTenant.execute(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(tenantId));
  }
}