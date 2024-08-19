package com.bizease.api.app.model.products.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bizease.api.app.exceptions.NotFoundException;
import com.bizease.api.app.model.products.dto.ProductsDTO;
import com.bizease.api.app.model.products.entities.Products;
import com.bizease.api.app.model.products.repository.ProductsRepository;

@Service

public class ProductsService {
    
    @Autowired
    private ProductsRepository productsRepository;

    public Products create(ProductsDTO productsDTO) {
        Optional<Products> productExits = this.productsRepository.findByUuid(productsDTO.getUuid());

        if (productExits.isPresent()) {
            throw new NotFoundException("Produto");
        }

        Products products = new Products(productsDTO);
        this.productsRepository.save(products);

        return products;
    } 

}
