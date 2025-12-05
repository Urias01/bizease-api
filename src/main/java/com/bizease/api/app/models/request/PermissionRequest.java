package com.bizease.api.app.models.request;

import com.bizease.api.app.models.enums.AccessProfile;

public record PermissionRequest(String name, AccessProfile accessLevel, Integer order, String viewId) {
}
