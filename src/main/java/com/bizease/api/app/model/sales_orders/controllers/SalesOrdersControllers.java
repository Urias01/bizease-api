package com.bizease.api.app.model.sales_orders.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bizease.api.app.exceptions.NotFoundException;
import com.bizease.api.app.model.sales_orders.dto.SalesOrdersDTO;
import com.bizease.api.app.model.sales_orders.entities.SalesOrders;
import com.bizease.api.app.model.sales_orders.useCases.CreateSalesOrderUseCase;
import com.bizease.api.app.model.sales_orders.useCases.UpdateSalesOrderUseCase;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/sales_orders")
public class SalesOrdersControllers {

    @Autowired
    private CreateSalesOrderUseCase createSalesOrderUseCase;
    @Autowired
    private UpdateSalesOrderUseCase updateSalesOrderUseCase;
    
    @PostMapping
     public ResponseEntity<Object> create(@RequestBody SalesOrdersDTO salesOrdersDTO) {
        try {
            SalesOrders salesOrders = this.createSalesOrderUseCase.execute(salesOrdersDTO);
            return ResponseEntity.status(201).body(salesOrders);
        }catch (NotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
     }
    
     @PutMapping("/{uuid}")
     public ResponseEntity<Object> update(@RequestBody SalesOrdersDTO salesOrdersDTO, @PathVariable String uuid) {
        try {
            SalesOrders salesOrders = this.updateSalesOrderUseCase.execute(salesOrdersDTO, uuid);
            return ResponseEntity.status(200).body(salesOrders);
        } catch(NotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch(Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
     }
    
}
