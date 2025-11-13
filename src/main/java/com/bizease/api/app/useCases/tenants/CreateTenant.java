package com.bizease.api.app.useCases.tenants;

import org.springframework.stereotype.Service;

import com.bizease.api.app.exceptions.AlreadyExistException;
import com.bizease.api.app.mappers.TenantMapper;
import com.bizease.api.app.models.Tenant;
import com.bizease.api.app.models.request.TenantRequest;
import com.bizease.api.app.models.response.CreateResponse;
import com.bizease.api.app.repositories.TenantRepository;
import com.bizease.api.app.useCases.users.CreateUser;

@Service
public class CreateTenant {

  private final TenantRepository tenantRepository;
  private final CreateUser createUser;

  public CreateTenant(TenantRepository tenantRepository, CreateUser createUser) {
    this.tenantRepository = tenantRepository;
    this.createUser = createUser;
  }

  public CreateResponse execute(TenantRequest request) {

    Tenant tenant = TenantMapper.toEntity(request);

    if(tenantRepository.findBySlug(tenant.getSlug()).isPresent()) {
      throw new AlreadyExistException("tenant with this name");
    }
    if (tenantRepository.findByDomain(tenant.getDomain()).isPresent()) {
      throw new AlreadyExistException("tenant with this domain");
    }

    tenantRepository.save(tenant);

    createUser.execute(request.user(), tenant.getId());

    return new CreateResponse(tenant.getId());
  }
}
