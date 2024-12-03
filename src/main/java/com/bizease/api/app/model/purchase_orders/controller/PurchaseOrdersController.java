package com.bizease.api.app.model.purchase_orders.controller;

import com.bizease.api.app.model.purchase_orders.dto.PurchaseOrdersRequestDTO;
import com.bizease.api.app.model.purchase_orders.entities.PurchaseOrders;
import com.bizease.api.app.model.purchase_orders.enums.StatusEnum;
import com.bizease.api.app.model.purchase_orders.useCases.*;

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
    private GetPurchaseOrderUseCase getPurchaseOrderUseCase;

    @Autowired
    private GetAllPurchaseOrdersUseCase getAllPurchaseOrdersUseCase;

    @Autowired
    private UpdatePurchaseOrderUseCase updatePurchaseOrderUseCase;

    @Autowired
    private UpdatePurchaseOrderStatusUseCase updatePurchaseOrderStatusUseCase;

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_OWNER')")
    public ResponseEntity<List<PurchaseOrders>> getAllPurchaseOrders() {
        var result = this.getAllPurchaseOrdersUseCase.execute();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{uuid}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_OWNER')")
    public ResponseEntity<Object> getPurchaseOrder(@PathVariable UUID uuid) {
        try {
            var result = this.getPurchaseOrderUseCase.execute(uuid);
            return ResponseEntity.ok(result);
        } catch (Exception exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_OWNER')")
    public ResponseEntity<Object> createPurchaseOrder(@RequestBody PurchaseOrdersRequestDTO purchaseOrdersRequestDTO, HttpServletRequest request) {
        try {
            purchaseOrdersRequestDTO.setCommerceUuid((String) request.getAttribute("commerce_uuid"));
            var result = this.createPurchaseOrderUseCase.execute(purchaseOrdersRequestDTO);
            return ResponseEntity.status(201).body(result);
        } catch (Exception exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

    @PutMapping("/{uuid}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_OWNER')")
    public ResponseEntity<Object> updatePurchaseOrder(@PathVariable UUID uuid, @RequestBody PurchaseOrdersRequestDTO purchaseOrdersRequestDTO) {
        try {
            var result = this.updatePurchaseOrderUseCase.execute(uuid, purchaseOrdersRequestDTO);
            return ResponseEntity.ok(result);
        } catch (Exception exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

    @PutMapping("/{uuid}/status")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_OWNER')")
    public ResponseEntity<Object> updatePurchaseOrderStatus(@PathVariable UUID uuid, @RequestParam("status") String status) {
        try {
            StatusEnum newStatus = StatusEnum.fromString(status);
            var result = this.updatePurchaseOrderStatusUseCase.execute(uuid, newStatus);
            return ResponseEntity.ok(result);
        } catch (Exception error) {
            return ResponseEntity.badRequest().body(error.getMessage());
        }
    }
}
