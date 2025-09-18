package com.bizease.api.app.model.sales_order_items.controllers;

import com.bizease.api.app.model.commerce.useCases.FindCommerceIdByUuidUseCase;
import com.bizease.api.app.model.sales_order_items.useCases.GetLostProductsUseCase;
import com.bizease.api.app.model.sales_order_items.useCases.UpdateSalesOrderItemsUseCase;
import com.bizease.api.app.responses.ApiResponse;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bizease.api.app.model.sales_order_items.dto.SalesOrderItemsDTO;
import com.bizease.api.app.model.sales_order_items.useCases.CreateSalesOrderItemsUseCase;

@RestController
@RequestMapping("/sales_order_items")
public class SalesOrderItemsController {

    @Autowired
    private CreateSalesOrderItemsUseCase createSalesOrderItemsUseCase;

    @Autowired
    private UpdateSalesOrderItemsUseCase updateSalesOrderItemsUseCase;

    @Autowired
    private GetLostProductsUseCase getLostProductsUseCase;

    @Autowired
    private FindCommerceIdByUuidUseCase findCommerceIdByUuidUseCase;

    @PostMapping
    public ResponseEntity<ApiResponse<Long>> create(@RequestBody SalesOrderItemsDTO salesOrderItemsDTO) {
        Long salesOrderItemsId = this.createSalesOrderItemsUseCase.execute(salesOrderItemsDTO);
        return ResponseEntity.status(201).body(ApiResponse.success(salesOrderItemsId, 201));
    }

    @GetMapping("/lost_products")
    public ResponseEntity<ApiResponse<?>> getLostProducts(HttpServletRequest request) {
        String commerceUuid = (String) request.getAttribute("commerce_uuid");
        Long comId = findCommerceIdByUuidUseCase.findIdByUuid(commerceUuid);
        var result = this.getLostProductsUseCase.execute(comId);
        return ResponseEntity.ok().body(ApiResponse.success(result, 200));
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<ApiResponse<Long>> update(
            @PathVariable String uuid,
            @RequestBody SalesOrderItemsDTO salesOrderItemsDTO) {
        Long salesOrderItemsId = this.updateSalesOrderItemsUseCase.execute(uuid, salesOrderItemsDTO);
        return ResponseEntity.ok().body(ApiResponse.success(salesOrderItemsId, 200));
    }
}
