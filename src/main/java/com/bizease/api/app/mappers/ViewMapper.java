package com.bizease.api.app.mappers;

import com.bizease.api.app.models.entities.View;
import com.bizease.api.app.models.request.ViewRequest;
import com.bizease.api.app.models.response.ViewResponse;

public class ViewMapper {
  
  public static View toEntity(ViewRequest request) {
    View view = new View();
    view.setName(request.name());
    return view;
  } 

  public static View toUpdate(View view, ViewRequest request) {
    view.setName(request.name());
    view.setIcon(request.icon());
    view.setName(request.name());
    view.setStatus(request.status());
    return view;
  }

  public static ViewResponse toResponse(View view) {
    return new ViewResponse(
        view.getId(),
        view.getName()
    );
  }
}
