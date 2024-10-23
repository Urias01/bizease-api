package com.bizease.api.app.model.inventories.controller;

import com.bizease.api.app.model.inventories.dto.InventoriesDTO;
import com.bizease.api.app.model.inventories.useCases.CreateInventoryUseCase;
import com.bizease.api.app.model.inventories.useCases.UpdateInventoryUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/inventories")
public class InventoriesController {

    @Autowired
    private CreateInventoryUseCase createInventoryUseCase;

    @Autowired
    private UpdateInventoryUseCase updateInventoryUseCase;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_OWNER')")
    public ResponseEntity<Object> createInventory(@RequestBody InventoriesDTO inventoriesDTO) {
        try {
            var result = this.createInventoryUseCase.execute(inventoriesDTO);
            return ResponseEntity.status(201).body(result);
        } catch (Exception exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

    @PutMapping("/{uuid}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_OWNER')")
    public ResponseEntity<Object> updateInventory(@PathVariable UUID uuid, @RequestBody InventoriesDTO inventoriesDTO) {
        try {
            var result = this.updateInventoryUseCase.execute(uuid, inventoriesDTO);
            return ResponseEntity.ok(result);
        } catch (Exception exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }
}
