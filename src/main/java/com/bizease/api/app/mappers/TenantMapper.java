package com.bizease.api.app.mappers;

import java.time.LocalDateTime;

import com.bizease.api.app.models.Tenant;
import com.bizease.api.app.models.User;
import com.bizease.api.app.models.request.TenantRequest;
import com.bizease.api.app.models.request.UserRequest;

public class TenantMapper {

  public static Tenant toEntity(TenantRequest request) {
    if (request == null) {
      return null;
    }

    Tenant tenant = new Tenant();
    tenant.setName(request.name());
    tenant.setEmail(request.email());
    tenant.setDomain(request.domain());
    tenant.setCreatedAt(LocalDateTime.now());
    tenant.setUpdatedAt(LocalDateTime.now());
    String slug = request.name().toLowerCase()
        .replaceAll("[^a-z0-9\\s]", "").replaceAll("\\s+", "-");
    tenant.setSlug(slug);

    return tenant;
  }

  public static TenantRequest toRequest(Tenant tenant) {
    if (tenant == null) {
      return null;
    }

    UserRequest userRequest = null;
    if (tenant.getUsers() != null && !tenant.getUsers().isEmpty()) {
      User user = tenant.getUsers().iterator().next();
      userRequest = new UserRequest(user.getName(), user.getEmail(), user.getPassword(), null, user.getType(),
          user.getStatus(), tenant.getId());
    }

    return new TenantRequest(tenant.getName(), tenant.getEmail(), tenant.getDomain(), userRequest);
  }
}
