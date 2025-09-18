package com.bizease.api.app.model.purchase_orders.controller;

import com.bizease.api.app.model.commons.PageReturn;
import com.bizease.api.app.model.purchase_orders.dto.PurchaseOrdersRequestDTO;
import com.bizease.api.app.model.purchase_orders.entities.PurchaseOrders;
import com.bizease.api.app.model.purchase_orders.enums.StatusEnum;
import com.bizease.api.app.model.purchase_orders.filter.PurchaseOrderFilter;
import com.bizease.api.app.model.purchase_orders.useCases.*;
import com.bizease.api.app.responses.ApiResponse;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/purchase_orders")
public class PurchaseOrdersController {

    @Autowired
    private CreatePurchaseOrderUseCase createPurchaseOrderUseCase;

    @Autowired
    private GetPurchaseOrderByUuid getPurchaseOrderByUuid;

    @Autowired
    private GetAllPurchaseOrdersUseCase getAllPurchaseOrdersUseCase;

    @Autowired
    private UpdatePurchaseOrderUseCase updatePurchaseOrderUseCase;

    @Autowired
    private UpdatePurchaseOrderStatusUseCase updatePurchaseOrderStatusUseCase;

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_OWNER')")
    public ResponseEntity<ApiResponse<PageReturn<List<PurchaseOrders>>>> getAllPurchaseOrders(
            PurchaseOrderFilter filter,
            HttpServletRequest request) {
        filter.setCommerceUuid((String) request.getAttribute("commerce_uuid"));
        PageReturn<List<PurchaseOrders>> resultList = this.getAllPurchaseOrdersUseCase.execute(filter);
        return ResponseEntity.ok().body(ApiResponse.success(resultList, 200));
    }

    @GetMapping("/{uuid}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_OWNER')")
    public ResponseEntity<ApiResponse<Object>> getPurchaseOrder(@PathVariable String uuid) {
        var result = this.getPurchaseOrderByUuid.execute(uuid);
        return ResponseEntity.ok().body(ApiResponse.success(result, 200));
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_OWNER')")
    public ResponseEntity<ApiResponse<Long>> createPurchaseOrder(
            @RequestBody PurchaseOrdersRequestDTO purchaseOrdersRequestDTO,
            HttpServletRequest request) {
        purchaseOrdersRequestDTO.setCommerceUuid((String) request.getAttribute("commerce_uuid"));
        Long purchaseOrdedrId = this.createPurchaseOrderUseCase.execute(purchaseOrdersRequestDTO);
        return ResponseEntity.status(201).body(ApiResponse.success(purchaseOrdedrId, 201));
    }

    @PutMapping("/{uuid}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_OWNER')")
    public ResponseEntity<ApiResponse<Long>> updatePurchaseOrder(
            @PathVariable UUID uuid,
            @RequestBody PurchaseOrdersRequestDTO purchaseOrdersRequestDTO) {
        Long purchaseOrdedrId = this.updatePurchaseOrderUseCase.execute(uuid, purchaseOrdersRequestDTO);
        return ResponseEntity.ok().body(ApiResponse.success(purchaseOrdedrId, 200));
    }

    @PutMapping("/{uuid}/status")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_OWNER')")
    public ResponseEntity<ApiResponse<Long>> updatePurchaseOrderStatus(
            @PathVariable UUID uuid,
            @RequestParam("status") String status) {
        StatusEnum newStatus = StatusEnum.fromString(status);
        Long purcharseOrderId = this.updatePurchaseOrderStatusUseCase.execute(uuid, newStatus);
        return ResponseEntity.ok().body(ApiResponse.success(purcharseOrderId,200));
    }
}
