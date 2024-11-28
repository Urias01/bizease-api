package com.bizease.api.app.model.products.useCases;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bizease.api.app.exceptions.AlreadyExistsException;
import com.bizease.api.app.exceptions.NotFoundException;
import com.bizease.api.app.model.categories.entities.Categories;
import com.bizease.api.app.model.categories.repository.CategoriesRepository;
import com.bizease.api.app.model.commerce.entities.Commerce;
import com.bizease.api.app.model.commerce.repository.CommerceRepository;
import com.bizease.api.app.model.products.dto.ProductsDTO;
import com.bizease.api.app.model.products.entities.Products;
import com.bizease.api.app.model.products.repository.ProductsRepository;

@Service
public class CreateProductUseCase {

    @Autowired
    private ProductsRepository productsRepository;

    @Autowired
    private CommerceRepository commerceRepository;

    @Autowired
    private CategoriesRepository categoriesRepository;

    public Products execute(ProductsDTO productsDTO) {

        this.productsRepository.findByNameAndCommerceUuid(productsDTO.getName(), productsDTO.getCommerceUuid())
                .ifPresent((products) -> {
                    throw new AlreadyExistsException("Produto");
                });

        Optional<Commerce> commerceExists = this.commerceRepository.findByUuid(productsDTO.getCommerceUuid());
        if (!commerceExists.isPresent()) {
            throw new NotFoundException("Comércio");
        }

        Optional<Categories> categoryExists = this.categoriesRepository
                .findByUuid(UUID.fromString(productsDTO.getCategoryUuid()));
        if (!categoryExists.isPresent()) {
            throw new NotFoundException("Categoria");
        }

        Products products = new Products(productsDTO, commerceExists.get(), categoryExists.get());

        this.productsRepository.save(products);

        return products;
    }

}
