package com.bizease.api.app.models.request;

import com.bizease.api.app.models.enums.AccessProfile;
import com.bizease.api.app.models.enums.ActivationsState;

public record ViewRequest(String name, String description, String route, String icon, AccessProfile maxAccessLevel,
    Integer order, ActivationsState status) {
}
