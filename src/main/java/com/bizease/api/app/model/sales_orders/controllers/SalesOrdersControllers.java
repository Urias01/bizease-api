package com.bizease.api.app.model.sales_orders.controllers;

import com.bizease.api.app.model.sales_orders.enums.SalesOrderStatus;
import com.bizease.api.app.model.sales_orders.useCases.UpdateSalesOrderStatusUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bizease.api.app.exceptions.NotFoundException;
import com.bizease.api.app.model.sales_orders.dto.SalesOrdersDTO;
import com.bizease.api.app.model.sales_orders.entities.SalesOrders;
import com.bizease.api.app.model.sales_orders.useCases.CreateSalesOrderUseCase;
import com.bizease.api.app.model.sales_orders.useCases.UpdateSalesOrderUseCase;
import com.bizease.api.app.model.sales_orders.useCases.DeleteSalesOrderUseCase;

import java.util.UUID;


@RestController
@RequestMapping("/sales_orders")
public class SalesOrdersControllers {

    @Autowired
    private CreateSalesOrderUseCase createSalesOrderUseCase;
    @Autowired
    private UpdateSalesOrderUseCase updateSalesOrderUseCase;
    @Autowired
    private DeleteSalesOrderUseCase deleteSalesOrderUseCase;
    @Autowired
    private UpdateSalesOrderStatusUseCase updateSalesOrderStatusUseCase;
    
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

     @PutMapping("/{uuid}/status")
     public ResponseEntity<Object> updateSalesOrderStatus(@PathVariable UUID uuid, @RequestParam("status") String status) {
        try {
            SalesOrderStatus newStatus = SalesOrderStatus.fromString(status);
            SalesOrders updatedOrder = this.updateSalesOrderStatusUseCase.execute(uuid, newStatus);
            return ResponseEntity.ok(updatedOrder);
        } catch (Exception error) {
            return ResponseEntity.badRequest().body(error.getMessage());
        }
     }

     @DeleteMapping("/{uuid}")
    public ResponseEntity<Object> delete(@PathVariable String uuid) {
        try {
            this.deleteSalesOrderUseCase.execute(uuid);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
    
}
