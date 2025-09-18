package com.bizease.api.app.model.purchase_order_items.controller;

import com.bizease.api.app.exceptions.NotFoundException;
import com.bizease.api.app.model.purchase_order_items.dto.PurchaseOrderItemDTO;
import com.bizease.api.app.model.purchase_order_items.entities.PurchaseOrderItem;
import com.bizease.api.app.model.purchase_order_items.useCases.CreatePurchaseOrderItemUseCase;
import com.bizease.api.app.model.purchase_order_items.useCases.GetAllPurchaseOrderItemsUseCase;
import com.bizease.api.app.model.purchase_order_items.useCases.GetPurchaseOrderItemUseCase;
import com.bizease.api.app.model.purchase_order_items.useCases.UpdatePurchaseOrderItemsUseCase;
import com.bizease.api.app.responses.ApiResponse;

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
    public ResponseEntity<ApiResponse<List<PurchaseOrderItem>>> getAllPurchaseOrderItems() {
        var result = this.getAllPurchaseOrderItemsUseCase.execute();
        return ResponseEntity.ok().body(ApiResponse.success(result, 200));
    }

    @GetMapping("/{uuid}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_OWNER')")
    public ResponseEntity<ApiResponse<Object>> getPurchaseOrderItem(@PathVariable UUID uuid) {
        var result = this.getPurchaseOrderItemUseCase.execute(uuid);
        return ResponseEntity.ok().body(ApiResponse.success(result, 200));
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_OWNER')")
    public ResponseEntity<ApiResponse<Long>> createPurchaseOrderItem(
            @RequestBody PurchaseOrderItemDTO purchaseOrderItemDTO) {
        Long purchaseOrderItemsId = this.createPurchaseOrderItemUseCase.execute(purchaseOrderItemDTO);
        return ResponseEntity.status(201).body(ApiResponse.success(purchaseOrderItemsId, 201));

    }

    @PutMapping("/{uuid}")
    public ResponseEntity<ApiResponse<Long>> update(
            @PathVariable String uuid,
            @RequestBody PurchaseOrderItemDTO purchaseOrderItemDTO) {
        Long purchaseOrderItemsId = this.updatePurchaseOrderItemsUseCase.execute(uuid,
                purchaseOrderItemDTO);
        return ResponseEntity.ok().body(ApiResponse.success(purchaseOrderItemsId, 200));
    }
}
