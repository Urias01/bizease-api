package com.bizease.api.app.model.sales_order_items.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bizease.api.app.exceptions.NotFoundException;
import com.bizease.api.app.model.sales_order_items.dto.SalesOrderItemsDTO;
import com.bizease.api.app.model.sales_order_items.entities.SalesOrderItems;
import com.bizease.api.app.model.sales_order_items.useCases.CreateSalesOrderItemsUseCase;

@RestController
@RequestMapping("/sales_order_items")
public class SalesOrderItemsController {

    @Autowired
    private CreateSalesOrderItemsUseCase createSalesOrderItemsUseCase;

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
    
}
