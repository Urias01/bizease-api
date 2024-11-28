package com.bizease.api.app.model.categories.useCases;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

  public PageReturn<List<Map<String, Object>>> execute(CategoriesFilter filter) {

    Specification<Categories> specification = where(commerceUuidEquals(filter.getCommerceUuid())
        .and(idEquals(filter.getId()))
        .and(nameLike(filter.getName())));

    Direction direction = Direction.valueOf(filter.getDirection().toUpperCase());

    PageRequest pageRequest = PageRequest.of(filter.getPage(), filter.getSize(), direction, filter.getField());

    Page<Categories> model = this.categoriesRepository.findAll(specification, pageRequest);

    List<Map<String, Object>> responses = model.getContent().stream().map(category -> {
      Map<String, Object> categoryMap = new HashMap<>();
      categoryMap.put("id", category.getId());
      categoryMap.put("uuid", category.getUuid());
      categoryMap.put("name", category.getName());
      categoryMap.put("description", category.getDescription());
      return categoryMap;
    }).toList();

    return new PageReturn<>(responses, model.getTotalElements(), pageRequest.getPageNumber(),
            pageRequest.getPageSize());

  }

}
