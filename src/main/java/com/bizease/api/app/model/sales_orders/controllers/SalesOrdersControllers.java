package com.bizease.api.app.model.sales_orders.controllers;

import com.bizease.api.app.model.commerce.useCases.FindCommerceIdByUuidUseCase;
import com.bizease.api.app.model.sales_orders.enums.SalesOrderStatus;
import com.bizease.api.app.model.sales_orders.useCases.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bizease.api.app.exceptions.NotFoundException;
import com.bizease.api.app.model.sales_orders.dto.SalesOrdersDTO;
import com.bizease.api.app.model.sales_orders.entities.SalesOrders;

import java.time.LocalDate;
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
    @Autowired
    private GetRevenueByPeriodUseCase getRevenueByPeriodUseCase;
    @Autowired
    private GetAnnualBuyingAndSellingUseCase getAnnualBuyingAndSellingUseCase;
    @Autowired
    private FindCommerceIdByUuidUseCase findCommerceIdByUuidUseCase;

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody SalesOrdersDTO salesOrdersDTO, HttpServletRequest request) {
        try {
            salesOrdersDTO.setCommerceUuid((String) request.getAttribute("commerce_uuid"));
            SalesOrders salesOrders = this.createSalesOrderUseCase.execute(salesOrdersDTO);
            return ResponseEntity.status(201).body(salesOrders);
        } catch (NotFoundException e) {
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
        } catch (NotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PutMapping("/{uuid}/status")
    public ResponseEntity<Object> updateSalesOrderStatus(@PathVariable UUID uuid,
            @RequestParam("status") String status) {
        try {
            SalesOrderStatus newStatus = SalesOrderStatus.fromString(status);
            SalesOrders updatedOrder = this.updateSalesOrderStatusUseCase.execute(uuid, newStatus);
            return ResponseEntity.ok(updatedOrder);
        } catch (Exception error) {
            return ResponseEntity.badRequest().body(error.getMessage());
        }
    }

    @GetMapping("/revenue")
    public ResponseEntity<?> getRevenueByPeriod(HttpServletRequest request,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        try {
            String commerceUuid = (String) request.getAttribute("commerce_uuid");
            Long comId = findCommerceIdByUuidUseCase.findIdByUuid(commerceUuid);

            var result = this.getRevenueByPeriodUseCase.execute(comId, startDate, endDate);
            return ResponseEntity.ok(result);
        } catch (Exception error) {
            return ResponseEntity.badRequest().body(error.getMessage());
        }
    }

    @GetMapping("/annual_buying_selling")
    public ResponseEntity<?> getAnnualBuyingAndSelling(HttpServletRequest request) {
        try {
            String commerceUuid = (String) request.getAttribute("commerce_uuid");
            Long comId = findCommerceIdByUuidUseCase.findIdByUuid(commerceUuid);

            var result = this.getAnnualBuyingAndSellingUseCase.execute(comId);
            return ResponseEntity.ok(result);
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
