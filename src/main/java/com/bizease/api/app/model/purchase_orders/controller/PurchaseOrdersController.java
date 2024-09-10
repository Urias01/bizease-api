package com.bizease.api.app.model.purchase_orders.controller;

import com.bizease.api.app.model.purchase_orders.dto.PurchaseOrdersRequestDTO;
import com.bizease.api.app.model.purchase_orders.entities.PurchaseOrders;
import com.bizease.api.app.model.purchase_orders.useCases.CreatePurchaseOrdersUseCase;
import com.bizease.api.app.model.purchase_orders.useCases.GetAllPurchaseOrdersUseCase;
import com.bizease.api.app.model.purchase_orders.useCases.GetPurchaseOrderUseCase;
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
    private CreatePurchaseOrdersUseCase createPurchaseOrdersUseCase;

    @Autowired
    private GetPurchaseOrderUseCase getPurchaseOrderUseCase;

    @Autowired
    GetAllPurchaseOrdersUseCase getAllPurchaseOrdersUseCase;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_OWNER')")
    public ResponseEntity<Object> createPurchaseOrder(@RequestBody PurchaseOrdersRequestDTO purchaseOrdersRequestDTO) {
        try {
            var result = this.createPurchaseOrdersUseCase.execute(purchaseOrdersRequestDTO);
            return ResponseEntity.ok(result);
        } catch (Exception exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

    @GetMapping("/{uuid}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_OWNER')")
    public ResponseEntity<Object> getPurchaseOrder(@PathVariable UUID uuid) {
        try {
            var result = this.getPurchaseOrderUseCase.execute(uuid);
            return ResponseEntity.status(201).body(result);
        } catch (Exception exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_OWNER')")
    public ResponseEntity<List<PurchaseOrders>> getAllPurchaseOrders() {
        var result = this.getAllPurchaseOrdersUseCase.execute();
        return ResponseEntity.ok(result);
    }
}
