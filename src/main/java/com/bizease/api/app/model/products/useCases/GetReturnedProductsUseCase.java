package com.bizease.api.app.model.products.useCases;

import com.bizease.api.app.exceptions.NotFoundException;
import com.bizease.api.app.model.commerce.entities.Commerce;
import com.bizease.api.app.model.commerce.repository.CommerceRepository;
import com.bizease.api.app.model.commons.PageReturn;
import com.bizease.api.app.model.products.filter.ReturnedProductFilter;
import com.bizease.api.app.model.products.repository.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class GetReturnedProductsUseCase {

    @Autowired
    private ProductsRepository productsRepository;

    @Autowired
    private CommerceRepository commerceRepository;

    public PageReturn<List<Map<String, Object>>> execute(ReturnedProductFilter filter) {

        Direction direction = Direction.valueOf(filter.getDirection().toUpperCase());

        PageRequest pageRequest = PageRequest.of(filter.getPage(), filter.getSize(), direction, filter.getField());

        Optional<Commerce> commerceExists = this.commerceRepository.findByUuid(filter.getCommerceUuid());

        if (!commerceExists.isPresent()) {
            throw new NotFoundException("Comércio");
        }

        Page<Map<String, Object>> model = productsRepository.findReturnedProducts(commerceExists.get().getId(),
                pageRequest);

        List<Map<String, Object>> responses = model.getContent();

        return new PageReturn<>(
                responses,
                model.getTotalElements(),
                model.getNumber(),
                model.getSize());
    }
}
