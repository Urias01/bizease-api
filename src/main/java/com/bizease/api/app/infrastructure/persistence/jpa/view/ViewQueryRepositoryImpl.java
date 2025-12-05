package com.bizease.api.app.infrastructure.persistence.jpa.view;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import com.bizease.api.app.models.commons.PagedResult;
import com.bizease.api.app.models.entities.View;
import com.bizease.api.app.models.filters.ViewFilter;
import com.bizease.api.app.ports.ViewQueryRepository;

import lombok.RequiredArgsConstructor;

import static com.bizease.api.app.infrastructure.persistence.specifications.ViewSpecification.*;
import static org.springframework.data.jpa.domain.Specification.where;

@Repository
@RequiredArgsConstructor
public class ViewQueryRepositoryImpl implements ViewQueryRepository {
  
  private final ViewRepository viewRepository;

  @Override
  public PagedResult<View> find(ViewFilter filter) {
    
        Specification<View> spec = where(idEquals(filter.getId()))
                .and(isActive(filter.getStatus()))
                .and(nameLike(filter.getName()));

        Direction direction = Direction.fromOptionalString(filter.getDirection())
                .orElse(Direction.ASC);

        PageRequest pageRequest = PageRequest.of(
                filter.getPage(),
                filter.getSize(),
                direction,
                filter.getField()
        );

        Page<View> result = viewRepository.findAll(spec, pageRequest);

        return new PagedResult<>(
                result.getContent(),
                result.getTotalElements(),
                result.getNumber(),
                result.getSize()
        );
  }
}
