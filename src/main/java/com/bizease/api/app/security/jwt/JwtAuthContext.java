package com.bizease.api.app.security.jwt;

public class JwtAuthContext {

  private static final ThreadLocal<JwtAuthContext> context = new ThreadLocal<>();

  private final String userId;
  private final String tenantId;

  public JwtAuthContext(String userId, String tenantId) {
    this.userId = userId;
    this.tenantId = tenantId;
  }

  public static void set(JwtAuthContext authContext) {
    context.set(authContext);
  }

  public static JwtAuthContext get() {
    return context.get();
  }

  public static void clear() {
    context.remove();
  }

  public String getUserId() {
    return userId;
  }

  public String getTenantId() {
    return tenantId;
  }
}

