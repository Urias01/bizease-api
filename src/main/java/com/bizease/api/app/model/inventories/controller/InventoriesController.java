package com.bizease.api.app.model.inventories.controller;

import com.bizease.api.app.model.inventories.dto.InventoriesDTO;
import com.bizease.api.app.model.inventories.useCases.CreateInventoryUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/inventories")
public class InventoriesController {

    @Autowired
    private CreateInventoryUseCase createInventoryUseCase;

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
}
