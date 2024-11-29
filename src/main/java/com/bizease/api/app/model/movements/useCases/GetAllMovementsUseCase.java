package com.bizease.api.app.model.movements.useCases;

import com.bizease.api.app.model.commerce.repository.CommerceRepository;
import com.bizease.api.app.model.commons.PageReturn;
import com.bizease.api.app.model.movements.entities.Movement;
import com.bizease.api.app.model.movements.filter.MovementFilter;
import com.bizease.api.app.model.movements.repositories.MovementRepository;

import static com.bizease.api.app.model.movements.specification.MovementSpecification.*;
import static org.springframework.data.jpa.domain.Specification.where;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class GetAllMovementsUseCase {

    @Autowired
    private MovementRepository movementRepository;

    public PageReturn<List<Movement>> execute(MovementFilter filter) {

        Specification<Movement> specification = where(commerceUuidEquals(filter.getCommerceUuid()))
                .and(idEquals(filter.getId()))
                .and(typeEnumEquals(filter.getType()));

        Direction direction = Direction.valueOf(filter.getDirection().toUpperCase());

        PageRequest pageRequest = PageRequest.of(filter.getPage(), filter.getSize(), direction, filter.getField());

        Page<Movement> model = this.movementRepository.findAll(specification, pageRequest);

        List<Movement> responses = model.getContent();

        return new PageReturn<List<Movement>>(responses, model.getTotalElements(), pageRequest.getPageNumber(),
                pageRequest.getPageSize());
    }

}
