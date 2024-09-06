package com.bizease.api.app.model.suppliers.useCases;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.bizease.api.app.model.commons.PageReturn;
import com.bizease.api.app.model.suppliers.entities.Suppliers;
import com.bizease.api.app.model.suppliers.filter.SuppliersFilter;
import com.bizease.api.app.model.suppliers.repository.SuppliersRepository;

import static com.bizease.api.app.model.suppliers.specification.SuppliersSpecification.*;
import static org.springframework.data.jpa.domain.Specification.where;

@Service
public class ListSuppliersUseCase {

    @Autowired
    private SuppliersRepository suppliersRepository;

    public PageReturn<List<Suppliers>> execute(SuppliersFilter filter) {

        Specification<Suppliers> specification = where(commerceUuidEquals(filter.getCommerceUuid())
                .and(nameLike(filter.getName())));

        Direction direction = Direction.valueOf(filter.getDirection().toUpperCase());

        PageRequest pageRequest = PageRequest.of(filter.getPage(), filter.getSize(), direction, filter.getField());

        Page<Suppliers> model = this.suppliersRepository.findAll(specification, pageRequest);

        List<Suppliers> responses = model.getContent();

        return new PageReturn<List<Suppliers>>(responses, model.getTotalElements());
    }

}
