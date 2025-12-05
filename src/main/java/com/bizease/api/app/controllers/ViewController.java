package com.bizease.api.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bizease.api.app.models.commons.PagedResult;
import com.bizease.api.app.models.filters.ViewFilter;
import com.bizease.api.app.models.request.ViewRequest;
import com.bizease.api.app.models.response.ApiResponse;
import com.bizease.api.app.models.response.CreateResponse;
import com.bizease.api.app.models.response.ViewResponse;
import com.bizease.api.app.services.views.CreateView;
import com.bizease.api.app.services.views.ListViews;
import com.bizease.api.app.services.views.UpdateView;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;




@RestController
@RequestMapping("/views")
public class ViewController {
  
  @Autowired
  private CreateView createView;
  @Autowired
  private UpdateView updateView;
  @Autowired
  private ListViews listViews;
 
  @PostMapping()
  public ResponseEntity<ApiResponse<CreateResponse>> create(@RequestBody ViewRequest request) {
    CreateResponse response = createView.execute(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(response));
  }

  @PutMapping("/{id}")
  public ResponseEntity<ApiResponse<CreateResponse>> update(@PathVariable String id, @RequestBody ViewRequest request) {
    CreateResponse response = updateView.execute(id, request);
    return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(response));
  }

  @GetMapping()
  public ResponseEntity<PagedResult<ViewResponse>> getMethodName(@RequestParam ViewFilter filters) {
      PagedResult<ViewResponse> pagedResult = listViews.execute(filters);
      return ResponseEntity.ok(pagedResult);
  }


}
