package com.bizease.api.app.model.suppliers.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
import com.bizease.api.app.model.suppliers.services.SuppliersService;
import com.bizease.api.app.model.suppliers.useCases.ListSuppliersUseCase;
import com.bizease.api.app.model.suppliers.useCases.UpdateSuppliersUseCase;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/suppliers")

public class SuppliersController {

    @Autowired
    private SuppliersService suppliersService;
    @Autowired
    private ListSuppliersUseCase listSuppliersUseCase;
    @Autowired UpdateSuppliersUseCase updateSuppliersUseCase;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_OWNER')")
    public ResponseEntity<Object> create(@RequestBody SuppliersDTO suppliersDTO) {
        try {
            Suppliers suppliers = this.suppliersService.create(suppliersDTO);
            return ResponseEntity.status(201).body(suppliers);
        } catch (NotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping()
    public PageReturn<List<Suppliers>> list(@ModelAttribute SuppliersFilter filter) {
        return this.listSuppliersUseCase.execute(filter);
      }

    @PutMapping("/{uuid}")
    public ResponseEntity<Object> update(@RequestBody SuppliersDTO suppliersDTO, @PathVariable String uuid) {
        try {
            Suppliers suppliers = this.updateSuppliersUseCase.execute(suppliersDTO, uuid);
            return ResponseEntity.status(200).body(suppliers);
        } catch (NotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

}
