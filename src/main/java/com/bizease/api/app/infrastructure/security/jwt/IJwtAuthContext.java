package com.bizease.api.app.infrastructure.security.jwt;

public interface IJwtAuthContext {

  String getUserId();

  String getTenantId();
}
