package com.bizease.api.app.model.purchase_orders.controller;

import com.bizease.api.app.model.purchase_orders.dto.PurchaseOrdersRequestDTO;
import com.bizease.api.app.model.purchase_orders.useCases.CreatePurchaseOrdersUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/purchase_orders")
public class PurchaseOrdersController {

    @Autowired
    private CreatePurchaseOrdersUseCase createPurchaseOrdersUseCase;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_OWNER')")
    public ResponseEntity<Object> createPurchaseOrder(@RequestBody PurchaseOrdersRequestDTO purchaseOrdersRequestDTO) {
        try {
            var result = this.createPurchaseOrdersUseCase.execute(purchaseOrdersRequestDTO);
            return ResponseEntity.status(201).body(result);
        } catch (Exception exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }
}
