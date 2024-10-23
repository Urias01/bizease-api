package com.bizease.api.app.model.inventories.controller;

import com.bizease.api.app.model.inventories.dto.InventoriesDTO;
import com.bizease.api.app.model.inventories.useCases.CreateInventoryUseCase;
import com.bizease.api.app.model.inventories.useCases.DeleteInventoryUseCase;
import com.bizease.api.app.model.inventories.useCases.GetInventoriesUseCase;
import com.bizease.api.app.model.inventories.useCases.UpdateInventoryUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/inventories")
public class InventoriesController {

    @Autowired
    private CreateInventoryUseCase createInventoryUseCase;

    @Autowired
    private UpdateInventoryUseCase updateInventoryUseCase;

    @Autowired
    private GetInventoriesUseCase getInventoriesUseCase;

    @Autowired
    private DeleteInventoryUseCase deleteInventoryUseCase;

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_OWNER')")
    public ResponseEntity<List> getAllInventories() {
        var result = this.getInventoriesUseCase.getAllInventories();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{uuid}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_OWNER')")
    public ResponseEntity<Object> getInventory(@PathVariable UUID uuid) {
        try {
            var result = this.getInventoriesUseCase.getByUuid(uuid);
            return ResponseEntity.ok(result);
        } catch (Exception exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

    @GetMapping("/commerce/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_OWNER')")
    public ResponseEntity<List> getInventoriesByCommerce(@PathVariable Long id) {
        var result = this.getInventoriesUseCase.getAllInventoriesFromCommerce(id);
        return ResponseEntity.ok(result);
    }

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

    @DeleteMapping("/{uuid}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_OWNER')")
    public ResponseEntity<Object> deleteInventory(@PathVariable UUID uuid) {
        try {
            this.deleteInventoryUseCase.execute(uuid);
            return ResponseEntity.ok("Inventário excluído com sucesso.");
        } catch (Exception exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }
}
