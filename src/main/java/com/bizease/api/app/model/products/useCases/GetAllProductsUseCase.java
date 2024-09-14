package com.bizease.api.app.model.products.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.bizease.api.app.model.commons.PageReturn;
import com.bizease.api.app.model.products.entities.Products;
import com.bizease.api.app.model.products.filter.ProductFilter;
import com.bizease.api.app.model.products.repository.ProductsRepository;

import static com.bizease.api.app.model.products.specification.ProductsSpecification.*;
import static org.springframework.data.jpa.domain.Specification.where;

import java.util.List;

@Service
public class GetAllProductsUseCase {

    @Autowired
    private ProductsRepository productsRepository;

    public PageReturn<List<Products>> execute(ProductFilter filter) {

        Specification<Products> specification = where(commerceUuidEquals(filter.getCommerceUuid())
        .and(nameLike(filter.getName())));

        Direction direction = Direction.valueOf(filter.getDirection().toUpperCase());

        PageRequest pageRequest = PageRequest.of(filter.getPage(), filter.getSize(), direction, filter.getField());

        Page<Products> model = this.productsRepository.findAll(specification, pageRequest);

        List<Products> responses = model.getContent();

        return new PageReturn<List<Products>>(responses, model.getTotalElements());
    }
}
