package com.bizease.api.app.models.response;

import com.bizease.api.app.models.enums.AccessProfile;

public record PermissionResponse(String id,AccessProfile accessLevel) {
  
}
