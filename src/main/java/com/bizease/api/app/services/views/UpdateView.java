package com.bizease.api.app.services.views;

import org.springframework.stereotype.Service;

import com.bizease.api.app.exceptions.NotFoundException;
import com.bizease.api.app.infrastructure.persistence.jpa.view.ViewRepository;
import com.bizease.api.app.mappers.ViewMapper;
import com.bizease.api.app.models.entities.View;
import com.bizease.api.app.models.request.ViewRequest;
import com.bizease.api.app.models.response.CreateResponse;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UpdateView {

  private ViewRepository viewRepository;
  public CreateResponse execute(String id, ViewRequest request) {
      
    View existingView = viewRepository.findById(id)
        .orElseThrow(() -> new NotFoundException("View"));

    existingView = ViewMapper.toUpdate(existingView, request);
    
    viewRepository.save(existingView);

    return new CreateResponse(existingView.getId());
  }

}
