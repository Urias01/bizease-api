package com.bizease.api.app.services.permissions;

import org.springframework.stereotype.Service;

import com.bizease.api.app.exceptions.NotFoundException;
import com.bizease.api.app.infrastructure.persistence.jpa.permission.PermissionRepository;
import com.bizease.api.app.infrastructure.persistence.jpa.view.ViewRepository;
import com.bizease.api.app.infrastructure.security.jwt.IJwtAuthContext;
import com.bizease.api.app.mappers.PermissionMapper;
import com.bizease.api.app.models.entities.Permission;
import com.bizease.api.app.models.entities.View;
import com.bizease.api.app.models.request.PermissionRequest;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CreatePermission {

  private PermissionRepository permissionRepository;
  private ViewRepository viewRepository;

  public String execute(PermissionRequest request) {
    View view = viewRepository.findById(request.viewId())
        .orElseThrow(() -> new NotFoundException("View"));

    Permission permission = PermissionMapper.toEntity(request);
    permission.setView(view);

    permissionRepository.save(permission);

    return permission.getId();
  }
}
