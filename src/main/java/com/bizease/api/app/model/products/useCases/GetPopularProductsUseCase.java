package com.bizease.api.app.model.products.useCases;

import com.bizease.api.app.model.products.repository.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class GetPopularProductsUseCase {

    @Autowired
    private ProductsRepository productsRepository;

    public List<Map<String, Object>> execute(Long comId) {
        return productsRepository.findPopularProducts(comId);
    }
}
