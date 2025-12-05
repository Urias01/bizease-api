package com.bizease.api.app.mappers;

import com.bizease.api.app.models.entities.Permission;
import com.bizease.api.app.models.request.PermissionRequest;
import com.bizease.api.app.models.response.PermissionResponse;

public class PermissionMapper {

  public static Permission toEntity(PermissionRequest request) {
    Permission permission = new Permission();
    permission.setAccessLevel(request.accessLevel());
    return permission;
  }

  public static PermissionResponse toResponse(Permission permission) {
    return new PermissionResponse(
        permission.getId(),
        permission.getAccessLevel());
  }
}
