package com.bizease.api.app.model.products.controllers;

import java.util.List;
import com.bizease.api.app.model.commerce.useCases.FindCommerceIdByUuidUseCase;
import com.bizease.api.app.model.products.useCases.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bizease.api.app.exceptions.NotFoundException;
import com.bizease.api.app.model.commons.PageReturn;
import com.bizease.api.app.model.products.dto.ProductsDTO;
import com.bizease.api.app.model.products.entities.Products;
import com.bizease.api.app.model.products.filter.ProductFilter;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/products")

public class ProductsController {

    @Autowired
    private CreateProductUseCase createProductUseCase;
    @Autowired
    private GetAllProductsUseCase getAllProducts;
    @Autowired
    private UpdateProductUseCase updateProductUseCase;
    @Autowired
    private DeleteProductUseCase deleteProductUseCase;
    @Autowired
    private GetPopularProductsUseCase getPopularProductsUseCase;
    @Autowired
    private FindCommerceIdByUuidUseCase findCommerceIdByUuidUseCase;
    @Autowired
    private GetProductByUuidUseCase getProductByUuidUseCase;
    @Autowired
    private GetExpiredProductsUseCase expiredProductsUseCase;
    @Autowired
    private GetReturnedProductsUseCase returnedProductsUseCase;

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody ProductsDTO productsDto, HttpServletRequest request) {
        try {
            productsDto.setCommerceUuid((String) request.getAttribute("commerce_uuid"));
            Products products = this.createProductUseCase.execute(productsDto);
            return ResponseEntity.status(201).body(products);
        } catch (NotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping
    public PageReturn<List<Products>> list(ProductFilter filter, HttpServletRequest request) {
        filter.setCommerceUuid((String) request.getAttribute("commerce_uuid"));
        return this.getAllProducts.execute(filter);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<Products> getByUuid(@PathVariable String uuid) {
        Products product = this.getProductByUuidUseCase.execute(uuid);

        return ResponseEntity.ok(product);
    }

    @GetMapping("/popular")
    public ResponseEntity<?> getPopularProducts(HttpServletRequest request) {
        try {
            String commerceUuid = (String) request.getAttribute("commerce_uuid");
            Long comId = findCommerceIdByUuidUseCase.findIdByUuid(commerceUuid);

            return ResponseEntity.ok(getPopularProductsUseCase.execute(comId));
        } catch (Exception error) {
            return ResponseEntity.internalServerError().body(error.getMessage());
        }
    }

    @GetMapping("/expired")
    public ResponseEntity<?> getExpiredProducts(HttpServletRequest request) {
        try {
            String commerceUuid = (String) request.getAttribute("commerce_uuid");
            Long comId = findCommerceIdByUuidUseCase.findIdByUuid(commerceUuid);

            return ResponseEntity.ok(expiredProductsUseCase.execute(comId));

        } catch (Exception error) {
            return ResponseEntity.internalServerError().body(error.getMessage());
        }
    }

    @GetMapping("/returned")
    public ResponseEntity<?> getReturnedProducts(HttpServletRequest request) {
        try {
            String commerceUuid = (String) request.getAttribute("commerce_uuid");
            Long comId = findCommerceIdByUuidUseCase.findIdByUuid(commerceUuid);

            return ResponseEntity.ok(returnedProductsUseCase.execute(comId));
        } catch (Exception error) {
            return ResponseEntity.internalServerError().body(error.getMessage());
        }
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<Object> update(@PathVariable String uuid, @RequestBody ProductsDTO productsDto,
            HttpServletRequest request) {
        try {
            productsDto.setCommerceUuid((String) request.getAttribute("commerce_uuid"));
            Products products = this.updateProductUseCase.execute(uuid, productsDto);
            return ResponseEntity.status(201).body(products);
        } catch (NotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Object> delete(@PathVariable String uuid) {
        try {
            this.deleteProductUseCase.execute(uuid);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

}
