package com.bizease.api.app.model.products.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bizease.api.app.exceptions.NotFoundException;
import com.bizease.api.app.model.commons.PageReturn;
import com.bizease.api.app.model.products.dto.ProductsDTO;
import com.bizease.api.app.model.products.entities.Products;
import com.bizease.api.app.model.products.filter.ProductFilter;
import com.bizease.api.app.model.products.useCases.CreateProductUseCase;
import com.bizease.api.app.model.products.useCases.GetAllProductsUseCase;

@RestController
@RequestMapping("/products")

public class ProductsController {
    
    @Autowired
    private CreateProductUseCase createProductUseCase;
    @Autowired
    private GetAllProductsUseCase getAllProducts;

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody ProductsDTO ProductsDTO) {
        try {
            Products products = this.createProductUseCase.execute(ProductsDTO);
            return ResponseEntity.status(201).body(products);
        } catch (NotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping
    public PageReturn<List<Products>> list(ProductFilter filter) {
        return this.getAllProducts.execute(filter);
    }
}
