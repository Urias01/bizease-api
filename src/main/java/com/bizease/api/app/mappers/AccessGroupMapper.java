package com.bizease.api.app.mappers;

import com.bizease.api.app.models.entities.AccessGroup;
import com.bizease.api.app.models.request.AccessGroupRequest;
import com.bizease.api.app.models.response.AccessGroupResponse;

public class AccessGroupMapper {

  public static AccessGroupResponse toResponse(AccessGroup accessGroup) {
    return new AccessGroupResponse(
        accessGroup.getId(),
        accessGroup.getName(),
        accessGroup.getDescription(),
        accessGroup.getStatus());
  }

  public static AccessGroup toEntity(AccessGroupRequest request) {
    AccessGroup accessGroup = new AccessGroup();
    accessGroup.setName(request.name());
    accessGroup.setDescription(request.description());
    accessGroup.setStatus(request.status());
    return accessGroup;
  }
}
