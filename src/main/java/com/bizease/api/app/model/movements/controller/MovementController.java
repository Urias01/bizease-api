package com.bizease.api.app.model.movements.controller;

import com.bizease.api.app.model.movements.dto.MovementDTO;
import com.bizease.api.app.model.movements.useCases.CreateMovementUseCase;
import com.bizease.api.app.model.movements.useCases.UpdateMovementUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/movements")
public class MovementController {

    @Autowired
    private CreateMovementUseCase createMovementUseCase;

    @Autowired
    private UpdateMovementUseCase updateMovementUseCase;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_OWNER')")
    public ResponseEntity<Object> createMovement(@RequestBody MovementDTO movementDTO) {
        try {
            var result = this.createMovementUseCase.execute(movementDTO);
            return ResponseEntity.status(201).body(result);
        } catch (Exception exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

    @PutMapping("/{uuid}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_OWNER')")
    public ResponseEntity<Object> updateMovement(@PathVariable UUID uuid, @RequestBody MovementDTO movementDTO) {
        try {
            var result = this.updateMovementUseCase.execute(uuid, movementDTO);
            return ResponseEntity.ok(result);
        } catch (Exception exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

}
