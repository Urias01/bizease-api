package com.bizease.api.app.model.sales_order_items.controllers;

import com.bizease.api.app.model.commerce.useCases.FindCommerceIdByUuidUseCase;
import com.bizease.api.app.model.sales_order_items.useCases.GetLostProductsUseCase;
import com.bizease.api.app.model.sales_order_items.useCases.UpdateSalesOrderItemsUseCase;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bizease.api.app.exceptions.NotFoundException;
import com.bizease.api.app.model.sales_order_items.dto.SalesOrderItemsDTO;
import com.bizease.api.app.model.sales_order_items.entities.SalesOrderItems;
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
    public ResponseEntity<Object> create(@RequestBody SalesOrderItemsDTO salesOrderItemsDTO) {
        try {
            SalesOrderItems salesOrderItems = this.createSalesOrderItemsUseCase.execute(salesOrderItemsDTO);
            return ResponseEntity.status(201).body(salesOrderItems);
        }catch (NotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/lost-products")
    public ResponseEntity<?> getLostProducts(HttpServletRequest request) {
        try {
            String commerceUuid = (String) request.getAttribute("commerce_uuid");
            Long comId = findCommerceIdByUuidUseCase.findIdByUuid(commerceUuid);

            var result = this.getLostProductsUseCase.execute(comId);
            return ResponseEntity.ok(result);
        } catch (Exception error) {
            return ResponseEntity.badRequest().body(error.getMessage());
        }
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<Object> update(@PathVariable String uuid, @RequestBody SalesOrderItemsDTO salesOrderItemsDTO) {
        try {
            var result = this.updateSalesOrderItemsUseCase.execute(uuid, salesOrderItemsDTO);
            return ResponseEntity.ok(result);
        } catch (Exception exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }
}
