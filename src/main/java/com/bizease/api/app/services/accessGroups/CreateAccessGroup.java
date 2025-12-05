package com.bizease.api.app.services.accessGroups;

import org.springframework.stereotype.Service;

import com.bizease.api.app.exceptions.NotFoundException;
import com.bizease.api.app.infrastructure.persistence.jpa.accessGroup.AccessGroupRepository;
import com.bizease.api.app.infrastructure.persistence.jpa.tenant.TenantRepository;
import com.bizease.api.app.infrastructure.security.jwt.IJwtAuthContext;
import com.bizease.api.app.mappers.AccessGroupMapper;
import com.bizease.api.app.models.entities.AccessGroup;
import com.bizease.api.app.models.entities.Tenant;
import com.bizease.api.app.models.request.AccessGroupRequest;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CreateAccessGroup {

  private AccessGroupRepository accessGroupRepository;
  private TenantRepository tenantRepository;
  private IJwtAuthContext jwtAuthContext;

  public void execute(AccessGroupRequest request) {
    AccessGroup accessGroup = AccessGroupMapper.toEntity(request);
    Tenant tenant = tenantRepository.findById(jwtAuthContext.getTenantId())
        .orElseThrow(() -> new NotFoundException("Tenant"));
    accessGroup.setTenant(tenant);


    accessGroupRepository.save(accessGroup);
  }
}
