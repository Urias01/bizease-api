package com.bizease.api.app.model.purchase_order_items.controller;

import com.bizease.api.app.exceptions.NotFoundException;
import com.bizease.api.app.model.purchase_order_items.dto.PurchaseOrderItemDTO;
import com.bizease.api.app.model.purchase_order_items.entities.PurchaseOrderItem;
import com.bizease.api.app.model.purchase_order_items.useCases.CreatePurchaseOrderItemUseCase;
import com.bizease.api.app.model.purchase_order_items.useCases.GetAllPurchaseOrderItemsUseCase;
import com.bizease.api.app.model.purchase_order_items.useCases.GetPurchaseOrderItemUseCase;
import com.bizease.api.app.model.purchase_order_items.useCases.UpdatePurchaseOrderItemsUseCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/purchase_order_items")
public class PurchaseOrderItemController {

    @Autowired
    private CreatePurchaseOrderItemUseCase createPurchaseOrderItemUseCase;

    @Autowired
    private GetPurchaseOrderItemUseCase getPurchaseOrderItemUseCase;

    @Autowired
    private GetAllPurchaseOrderItemsUseCase getAllPurchaseOrderItemsUseCase;

    @Autowired
    private UpdatePurchaseOrderItemsUseCase updatePurchaseOrderItemsUseCase;

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_OWNER')")
    public ResponseEntity<List<PurchaseOrderItem>> getAllPurchaseOrderItems() {
        var result = this.getAllPurchaseOrderItemsUseCase.execute();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{uuid}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_OWNER')")
    public ResponseEntity<Object> getPurchaseOrderItem(@PathVariable UUID uuid) {
        try {
            var result = this.getPurchaseOrderItemUseCase.execute(uuid);
            return ResponseEntity.ok(result);
        } catch (Exception exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

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

    @PutMapping("/{uuid}")
    public ResponseEntity<Object> update(@PathVariable String uuid, @RequestBody PurchaseOrderItemDTO purchaseOrderItemDTO) {
        try {
            PurchaseOrderItem purchaseOrderItem = this.updatePurchaseOrderItemsUseCase.execute(uuid, purchaseOrderItemDTO);
            return ResponseEntity.status(200).body(purchaseOrderItem);
        } catch (NotFoundException exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        } catch (Exception exception) {
            return ResponseEntity.internalServerError().body(exception.getMessage());
        }
    }
}
