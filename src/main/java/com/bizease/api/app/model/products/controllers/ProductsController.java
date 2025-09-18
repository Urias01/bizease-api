package com.bizease.api.app.model.products.controllers;

import java.util.List;
import java.util.Map;

import com.bizease.api.app.model.commerce.useCases.FindCommerceIdByUuidUseCase;
import com.bizease.api.app.model.products.useCases.*;
import com.bizease.api.app.responses.ApiResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bizease.api.app.exceptions.NotFoundException;
import com.bizease.api.app.model.commons.PageReturn;
import com.bizease.api.app.model.products.dto.ProductsDTO;
import com.bizease.api.app.model.products.entities.Products;
import com.bizease.api.app.model.products.filter.ProductExpiredFilter;
import com.bizease.api.app.model.products.filter.ProductFilter;
import com.bizease.api.app.model.products.filter.ReturnedProductFilter;

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
    public ResponseEntity<ApiResponse<Long>> create(
            @RequestBody ProductsDTO productsDto,
            HttpServletRequest request) {
        productsDto.setCommerceUuid((String) request.getAttribute("commerce_uuid"));
        Long productId = this.createProductUseCase.execute(productsDto);
        return ResponseEntity.status(201).body(ApiResponse.success(productId, 201));
    }

    @GetMapping
    public ApiResponse<PageReturn<List<Products>>> list(ProductFilter filter, HttpServletRequest request) {
        filter.setCommerceUuid((String) request.getAttribute("commerce_uuid"));
        PageReturn<List<Products>> productsList = this.getAllProducts.execute(filter);
        return ApiResponse.success(productsList, 200);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<ApiResponse<Products>> getByUuid(@PathVariable String uuid) {
        Products product = this.getProductByUuidUseCase.execute(uuid);

        return ResponseEntity.ok().body(ApiResponse.success(product, 200));
    }

    @GetMapping("/popular")
    public ResponseEntity<ApiResponse<?>> getPopularProducts(HttpServletRequest request) {
        String commerceUuid = (String) request.getAttribute("commerce_uuid");
        Long comId = findCommerceIdByUuidUseCase.findIdByUuid(commerceUuid);
        var result = getPopularProductsUseCase.execute(comId);
        return ResponseEntity.ok().body(ApiResponse.success(result, 200));
    }

    @GetMapping("/expired")
    public ResponseEntity<ApiResponse<?>> getExpiredProducts(ProductExpiredFilter filter, HttpServletRequest request) {
        filter.setCommerceUuid((String) request.getAttribute("commerce_uuid"));
        var result = expiredProductsUseCase.execute(filter);
        return ResponseEntity.ok().body(ApiResponse.success(result, 200));
    }

    @GetMapping("/returned")
    public ResponseEntity<ApiResponse<?>> getReturnedProducts(ReturnedProductFilter filter,
            HttpServletRequest request) {
        filter.setCommerceUuid((String) request.getAttribute("commerce_uuid"));
        var result = returnedProductsUseCase.execute(filter);
        return ResponseEntity.ok().body(ApiResponse.success(result, 200));
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<ApiResponse<Long>> update(
            @PathVariable String uuid,
            @RequestBody ProductsDTO productsDto,
            HttpServletRequest request) {
        productsDto.setCommerceUuid((String) request.getAttribute("commerce_uuid"));
        Long productId = this.updateProductUseCase.execute(uuid, productsDto);
        return ResponseEntity.ok().body(ApiResponse.success(productId, 200));
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable String uuid) {
        this.deleteProductUseCase.execute(uuid);
        return ResponseEntity.ok().body(ApiResponse.success(null, 200));
    }

}
