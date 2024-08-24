package com.bizease.api.app.model.categories.useCases;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.bizease.api.app.model.categories.repository.CategoriesRepository;
import com.bizease.api.app.model.categories.entities.Categories;
import com.bizease.api.app.model.categories.filter.CategoriesFilter;
import com.bizease.api.app.model.commons.PageReturn;

import static com.bizease.api.app.model.categories.specification.CategoriesSpecification.*;
import static org.springframework.data.jpa.domain.Specification.where;

@Service
public class GetAllCategoriesUseCase {

  @Autowired
  private CategoriesRepository categoriesRepository;

  public PageReturn<List<Categories>> execute(CategoriesFilter filter) {

    Specification<Categories> specification = where(commerceUuidEquals(filter.getCommerceUuid())
        .and(nameLike(filter.getName())));

    Direction direction = Direction.valueOf(filter.getDirection().toUpperCase());

    PageRequest pageRequest = PageRequest.of(filter.getPage(), filter.getSize(), direction, filter.getField());

    Page<Categories> model = this.categoriesRepository.findAll(specification, pageRequest);

    List<Categories> responses = model.getContent();

    return new PageReturn<List<Categories>>(responses, model.getTotalElements());

  }

}
