package com.bizease.api.app.model.suppliers.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bizease.api.app.exceptions.NotFoundException;
import com.bizease.api.app.model.commons.PageReturn;
import com.bizease.api.app.model.suppliers.dto.SuppliersDTO;
import com.bizease.api.app.model.suppliers.entities.Suppliers;
import com.bizease.api.app.model.suppliers.filter.SuppliersFilter;
import com.bizease.api.app.model.suppliers.useCases.CreateSuppliersUseCase;
import com.bizease.api.app.model.suppliers.useCases.DeleteSuppliersUseCase;
import com.bizease.api.app.model.suppliers.useCases.GetSupplierByUuidUseCase;
import com.bizease.api.app.model.suppliers.useCases.ListSuppliersUseCase;
import com.bizease.api.app.model.suppliers.useCases.UpdateSuppliersUseCase;
import com.bizease.api.app.responses.ApiResponse;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/suppliers")

public class SuppliersController {

    @Autowired
    private CreateSuppliersUseCase createSuppliersUseCase;
    @Autowired
    private ListSuppliersUseCase listSuppliersUseCase;
    @Autowired
    private GetSupplierByUuidUseCase getSupplierByUuidUseCase;
    @Autowired
    private UpdateSuppliersUseCase updateSuppliersUseCase;
    @Autowired
    private DeleteSuppliersUseCase deleteSuppliersUseCase;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_OWNER')")
    public ResponseEntity<ApiResponse<Long>> create(
            @RequestBody SuppliersDTO suppliersDTO,
            HttpServletRequest request) {
        String commerceUuid = (String) request.getAttribute("commerce_uuid");
        suppliersDTO.setCommerceUuid(commerceUuid);
        Long suppliersId = this.createSuppliersUseCase.execute(suppliersDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(suppliersId, 201));
    }

    @GetMapping()
    public PageReturn<List<Suppliers>> list(@ModelAttribute SuppliersFilter filter, HttpServletRequest request) {
        String commerceUuid = (String) request.getAttribute("commerce_uuid");
        filter.setCommerceUuid(commerceUuid);

        return this.listSuppliersUseCase.execute(filter);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<ApiResponse<Suppliers>> getByUuid(@PathVariable String uuid) {
        Suppliers suppliers = this.getSupplierByUuidUseCase.execute(uuid);
        return ResponseEntity.ok().body(ApiResponse.success(suppliers, 200));
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<ApiResponse<Long>> update(
            @RequestBody SuppliersDTO suppliersDTO,
            @PathVariable String uuid,
            HttpServletRequest request) {
        String commerceUuid = (String) request.getAttribute("commerce_uuid");
        suppliersDTO.setCommerceUuid(commerceUuid);
        Long suppliersId = this.updateSuppliersUseCase.execute(suppliersDTO, uuid);
        return ResponseEntity.ok().body(ApiResponse.success(suppliersId, 200));
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable String uuid) {
        this.deleteSuppliersUseCase.execute(uuid);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ApiResponse.success(null, 204));
    }
}
