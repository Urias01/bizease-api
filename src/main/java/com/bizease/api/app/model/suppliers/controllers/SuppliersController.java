package com.bizease.api.app.model.suppliers.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
import com.bizease.api.app.model.suppliers.useCases.ListSuppliersUseCase;
import com.bizease.api.app.model.suppliers.useCases.UpdateSuppliersUseCase;

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
    private UpdateSuppliersUseCase updateSuppliersUseCase;
    @Autowired
    private DeleteSuppliersUseCase deleteSuppliersUseCase;

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody SuppliersDTO suppliersDTO) {
        try {
            Suppliers suppliers = this.createSuppliersUseCase.execute(suppliersDTO);
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

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Object> delete(@PathVariable String uuid) {
        try {
            this.deleteSuppliersUseCase.execute(uuid);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
