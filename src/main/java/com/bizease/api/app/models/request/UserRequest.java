package com.bizease.api.app.models.request;

import com.bizease.api.app.models.enums.AccessProfile;
import com.bizease.api.app.models.enums.AccessStatus;

public record UserRequest(String name, String email, String password, String confirmPassword, AccessProfile type, AccessStatus status, String tenantId) {
}
