package com.bizease.api.app.models.response;

import com.bizease.api.app.models.enums.AccessGroupStatus;

public record AccessGroupResponse(String id, String name, String description, AccessGroupStatus status) {

}
