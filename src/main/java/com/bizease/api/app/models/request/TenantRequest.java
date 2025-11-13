package com.bizease.api.app.models.request;

public record TenantRequest(String name, String email, String domain, UserRequest user) {
}
