package com.bizease.api.app.model.movements.controller;

import com.bizease.api.app.model.commons.PageReturn;
import com.bizease.api.app.model.movements.dto.MovementDTO;
import com.bizease.api.app.model.movements.entities.Movement;
import com.bizease.api.app.model.movements.filter.MovementFilter;
import com.bizease.api.app.model.movements.useCases.CreateMovementUseCase;
import com.bizease.api.app.model.movements.useCases.GetAllMovementsUseCase;
import com.bizease.api.app.model.movements.useCases.UpdateMovementUseCase;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/movements")
public class MovementController {

    @Autowired
    private CreateMovementUseCase createMovementUseCase;

    @Autowired
    private UpdateMovementUseCase updateMovementUseCase;

    @Autowired
    private GetAllMovementsUseCase getAllMovementsUseCase;

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_OWNER')")
    public PageReturn<List<Movement>>  getAllMovements(MovementFilter filter, HttpServletRequest request) {
        filter.setCommerceUuid((String) request.getAttribute("commerce_uuid"));
        return this.getAllMovementsUseCase.execute(filter);
    }

    // @GetMapping("/{uuid}")
    // @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_OWNER')")
    // public ResponseEntity<Object> getMovement(@PathVariable UUID uuid) {
    //     try {
    //         var result = this.getMovementsUseCase.getByUuid(uuid);
    //         return ResponseEntity.ok(result);
    //     } catch (Exception exception) {
    //         return ResponseEntity.badRequest().body(exception.getMessage());
    //     }
    // }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_OWNER')")
    public ResponseEntity<Object> createMovement(@RequestBody MovementDTO movementDTO, HttpServletRequest request) {
        try {
            movementDTO.setCommerceUuid((String) request.getAttribute("commerce_uuid"));
            movementDTO.setUserUuid((String) request.getAttribute("user_uuid"));
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
