package com.bizease.api.app.model.purchase_order_items.controller;

import com.bizease.api.app.model.purchase_order_items.dto.PurchaseOrderItemDTO;
import com.bizease.api.app.model.purchase_order_items.useCases.CreatePurchaseOrderItemUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/purchase_order_items")
public class PurchaseOrderItemController {

    @Autowired
    private CreatePurchaseOrderItemUseCase createPurchaseOrderItemUseCase;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_OWNER')")
    public ResponseEntity<Object> createPurchaseOrderItem(@RequestBody PurchaseOrderItemDTO purchaseOrderItemDTO) {
        try {
            var result = this.createPurchaseOrderItemUseCase.execute(purchaseOrderItemDTO);
            return ResponseEntity.status(201).body(result);
        } catch (Exception exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }
}
