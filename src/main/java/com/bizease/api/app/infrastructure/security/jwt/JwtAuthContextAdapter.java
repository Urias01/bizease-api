package com.bizease.api.app.infrastructure.security.jwt;

import org.springframework.stereotype.Component;
@Component
public class JwtAuthContextAdapter implements IJwtAuthContext {

    @Override
    public String getUserId() {
        JwtAuthContext ctx = JwtAuthContext.get();
        return ctx != null ? ctx.getUserId() : null;
    }

    @Override
    public String getTenantId() {
        JwtAuthContext ctx = JwtAuthContext.get();
        return ctx != null ? ctx.getTenantId() : null;
    }
}
