package com.bizease.api.app.model.products.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bizease.api.app.exceptions.AlreadyExistsException;
import com.bizease.api.app.exceptions.NotFoundException;
import com.bizease.api.app.model.commerce.entities.Commerce;
import com.bizease.api.app.model.commerce.repository.CommerceRepository;
import com.bizease.api.app.model.products.dto.ProductsDTO;
import com.bizease.api.app.model.products.entities.Products;
import com.bizease.api.app.model.products.repository.ProductsRepository;

@Service

public class ProductsService {
    
    @Autowired
    private ProductsRepository productsRepository;

    @Autowired
    private CommerceRepository commerceRepository;

    public Products create(ProductsDTO productsDTO) {
        
        this.productsRepository.findByNameAndCommerceId(productsDTO.getName(), productsDTO.getCommerceId())
        .ifPresent((products)->{
            throw new AlreadyExistsException("Produto");
        });

        Optional<Commerce> commerceExists = this.commerceRepository.findById(productsDTO.getCommerceId());
        if (!commerceExists.isPresent()) {
            throw new NotFoundException("Comércio");
        }

        Products products = new Products(productsDTO, commerceExists.get());
        this.productsRepository.save(products);

        return products;
    } 

}
