package com.bizease.api.app.security.jwt;

public interface IJwtAuthContext {

  String getUserId();

  String getTenantId();
}
