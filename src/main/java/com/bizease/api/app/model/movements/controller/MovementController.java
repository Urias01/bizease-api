package com.bizease.api.app.model.movements.controller;

import com.bizease.api.app.model.movements.dto.MovementDTO;
import com.bizease.api.app.model.movements.useCases.CreateMovementUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/movements")
public class MovementController {

    @Autowired
    private CreateMovementUseCase createMovementUseCase;

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
}
