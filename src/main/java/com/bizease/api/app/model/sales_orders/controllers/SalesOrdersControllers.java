package com.bizease.api.app.model.sales_orders.controllers;

import com.bizease.api.app.model.commerce.useCases.FindCommerceIdByUuidUseCase;
import com.bizease.api.app.model.commons.PageReturn;
import com.bizease.api.app.model.sales_orders.enums.SalesOrderStatus;
import com.bizease.api.app.model.sales_orders.filter.SalesOrderFilter;
import com.bizease.api.app.model.sales_orders.useCases.*;
import com.bizease.api.app.responses.ApiResponse;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bizease.api.app.exceptions.NotFoundException;
import com.bizease.api.app.model.sales_orders.dto.SalesOrdersDTO;
import com.bizease.api.app.model.sales_orders.entities.SalesOrders;

import java.time.LocalDate;
import java.util.List;
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
    @Autowired
    private GetAllSalesOrderUseCase getAllSalesOrderUseCase;
    @Autowired
    private GetSalesOrderByUuid getSalesOrderByUuid;

    @GetMapping
    public ResponseEntity<ApiResponse<PageReturn<List<SalesOrders>>>> getAllSalesOrders(
            SalesOrderFilter filter,
            HttpServletRequest request) {
        filter.setCommerceUuid((String) request.getAttribute("commerce_uuid"));
        PageReturn<List<SalesOrders>> result = this.getAllSalesOrderUseCase.execute(filter);
        return ResponseEntity.ok().body(ApiResponse.success(result, 200));
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<ApiResponse<SalesOrders>> getSalesOrderByUuid(@PathVariable String uuid) {
        SalesOrders salesOrder = this.getSalesOrderByUuid.execute(uuid);
        return ResponseEntity.ok().body(ApiResponse.success(salesOrder, 200));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Long>> create(
            @RequestBody SalesOrdersDTO salesOrdersDTO,
            HttpServletRequest request) {
        salesOrdersDTO.setCommerceUuid((String) request.getAttribute("commerce_uuid"));
        Long salesOrderId = this.createSalesOrderUseCase.execute(salesOrdersDTO);
        return ResponseEntity.status(201).body(ApiResponse.success(salesOrderId, 201));
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<ApiResponse<Long>> update(
            @RequestBody SalesOrdersDTO salesOrdersDTO,
            @PathVariable String uuid) {
        Long salesOrderId = this.updateSalesOrderUseCase.execute(salesOrdersDTO, uuid);
        return ResponseEntity.ok().body(ApiResponse.success(salesOrderId, 200));
    }

    @PutMapping("/{uuid}/status")
    public ResponseEntity<ApiResponse<Object>> updateSalesOrderStatus(
            @PathVariable UUID uuid,
            @RequestParam("status") String status) {
        SalesOrderStatus newStatus = SalesOrderStatus.fromString(status);
        Long updatedOrderId = this.updateSalesOrderStatusUseCase.execute(uuid, newStatus);
        return ResponseEntity.ok().body(ApiResponse.success(updatedOrderId, 200));

    }

    @GetMapping("/revenue")
    public ResponseEntity<ApiResponse<?>> getRevenueByPeriod(
            HttpServletRequest request,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        String commerceUuid = (String) request.getAttribute("commerce_uuid");
        Long comId = findCommerceIdByUuidUseCase.findIdByUuid(commerceUuid);

        var result = this.getRevenueByPeriodUseCase.execute(comId, startDate, endDate);
        return ResponseEntity.ok().body(ApiResponse.success(result, 200));
    }

    @GetMapping("/annual_buying_selling")
    public ResponseEntity<ApiResponse<?>> getAnnualBuyingAndSelling(HttpServletRequest request) {
        String commerceUuid = (String) request.getAttribute("commerce_uuid");
        Long comId = findCommerceIdByUuidUseCase.findIdByUuid(commerceUuid);
        var result = this.getAnnualBuyingAndSellingUseCase.execute(comId);
        return ResponseEntity.ok().body(ApiResponse.success(result, 200));
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable String uuid) {
        this.deleteSalesOrderUseCase.execute(uuid);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ApiResponse.success(null, 204));
    }

}
