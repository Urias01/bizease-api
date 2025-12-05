package com.bizease.api.app.models.request;

import com.bizease.api.app.models.enums.AccessGroupStatus;

public record AccessGroupRequest(String name, String description, AccessGroupStatus status, String[] views) {

}
