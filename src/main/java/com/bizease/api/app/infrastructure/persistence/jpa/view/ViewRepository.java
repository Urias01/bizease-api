package com.bizease.api.app.infrastructure.persistence.jpa.view;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.bizease.api.app.models.commons.PagedResult;
import com.bizease.api.app.models.entities.View;
import com.bizease.api.app.models.filters.ViewFilter;

public interface ViewRepository extends JpaRepository<View, String>, JpaSpecificationExecutor<View>  {

  Optional<View> findById(String id);
  
  @Query("SELECT v FROM View v WHERE (:#{#filter.id} IS NULL OR v.id = :#{#filter.id}) "
      + "AND (:#{#filter.name} IS NULL OR LOWER(v.name) LIKE LOWER(CONCAT('%', :#{#filter.name}, '%'))) "
      + "AND (:#{#filter.status} IS NULL OR v.status = :#{#filter.status})")
  PagedResult<View> find(ViewFilter filter);
}
