package com.bizease.api.app.services.views;

import org.springframework.stereotype.Service;

import com.bizease.api.app.infrastructure.persistence.jpa.view.ViewRepository;
import com.bizease.api.app.mappers.ViewMapper;
import com.bizease.api.app.models.entities.View;
import com.bizease.api.app.models.request.ViewRequest;
import com.bizease.api.app.models.response.CreateResponse;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CreateView {
  
  private ViewRepository viewRepository;

  public CreateResponse execute(ViewRequest request) {
    View view = ViewMapper.toEntity(request);

    viewRepository.save(view);

    return new CreateResponse(view.getId());
  }

}
