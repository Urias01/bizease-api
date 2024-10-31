package com.bizease.api.app.model.products.useCases;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bizease.api.app.exceptions.NotFoundException;
import com.bizease.api.app.model.commons.enums.IsActiveEnum;
import com.bizease.api.app.model.products.entities.Products;
import com.bizease.api.app.model.products.repository.ProductsRepository;

@Service
public class DeleteProductUseCase {
    
    @Autowired
    ProductsRepository productsRepository;

    public void execute (String uuid) {
        Optional<Products> productExists = productsRepository.findByUuid(uuid);

        if (!productExists.isPresent()) {
            throw new NotFoundException("Produto");
        }

        Products product = productExists.get();

        product.setIsActive(IsActiveEnum.INACTIVE);

        this.productsRepository.save(product);
    }

}
