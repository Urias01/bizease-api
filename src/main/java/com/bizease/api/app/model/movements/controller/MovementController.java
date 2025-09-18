package com.bizease.api.app.model.movements.controller;

import com.bizease.api.app.model.commons.PageReturn;
import com.bizease.api.app.model.movements.dto.MovementDTO;
import com.bizease.api.app.model.movements.entities.Movement;
import com.bizease.api.app.model.movements.filter.MovementFilter;
import com.bizease.api.app.model.movements.useCases.CreateMovementUseCase;
import com.bizease.api.app.model.movements.useCases.GetAllMovementsUseCase;
import com.bizease.api.app.model.movements.useCases.GetMovementsByUuidUseCase;
import com.bizease.api.app.model.movements.useCases.UpdateMovementUseCase;
import com.bizease.api.app.responses.ApiResponse;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @Autowired
    private GetMovementsByUuidUseCase getMovementsByUuidUseCase;

    @GetMapping
    public PageReturn<List<Movement>> getAllMovements(MovementFilter filter, HttpServletRequest request) {
        filter.setCommerceUuid((String) request.getAttribute("commerce_uuid"));
        return this.getAllMovementsUseCase.execute(filter);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<ApiResponse<Object>> getMovementByUuid(@PathVariable String uuid) {
        var result = this.getMovementsByUuidUseCase.execute(uuid);
        return ResponseEntity.ok().body(ApiResponse.success(result, 200));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Long>> createMovement(
            @RequestBody MovementDTO movementDTO,
            HttpServletRequest request) {
        movementDTO.setCommerceUuid((String) request.getAttribute("commerce_uuid"));
        movementDTO.setUserUuid((String) request.getAttribute("user_uuid"));
        Long movementId = this.createMovementUseCase.execute(movementDTO);
        return ResponseEntity.status(201).body(ApiResponse.success(movementId, 201));
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<ApiResponse<Long>> updateMovement(
            @PathVariable UUID uuid,
            @RequestBody MovementDTO movementDTO,
            HttpServletRequest request) {
        movementDTO.setCommerceUuid((String) request.getAttribute("commerce_uuid"));
        movementDTO.setUserUuid((String) request.getAttribute("user_uuid"));
        Long movementId = this.updateMovementUseCase.execute(uuid, movementDTO);
        return ResponseEntity.status(201).body(ApiResponse.success(movementId, 200));
    }

}
