package com.bizease.api.app.useCases.tenants;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bizease.api.app.exceptions.AlreadyExistException;
import com.bizease.api.app.models.Tenant;
import com.bizease.api.app.models.request.TenantRequest;
import com.bizease.api.app.models.request.UserRequest;
import com.bizease.api.app.models.response.CreateResponse;
import com.bizease.api.app.repositories.TenantRepository;
import com.bizease.api.app.useCases.users.CreateUser;

@ExtendWith(MockitoExtension.class)
public class CreateTenantTest {

  @Mock
  private TenantRepository tenantRepository;
  @Mock
  private CreateUser createUser;

  @InjectMocks
  private CreateTenant createTenant;

  @Test
  @DisplayName("Should be able to create a new tenant")
  public void shouldBeAbleToCreateNewTenant() {
    Tenant tenant = new Tenant();
    tenant.setId("tenant-123");
    tenant.setSlug("tenantname");
    tenant.setDomain("tenant-domain.com");

    when(tenantRepository.findBySlug(tenant.getSlug())).thenReturn(Optional.empty());
    when(tenantRepository.findByDomain(tenant.getDomain())).thenReturn(Optional.empty());

    when(tenantRepository.save(any(Tenant.class))).thenAnswer(invocation -> {
      Tenant savedTenant = invocation.getArgument(0);
      savedTenant.setId("tenant-123");
      return savedTenant;
    });

    TenantRequest request = new TenantRequest(
        "tenantname",
        "john.doe@example.com",
        "tenant-domain.com", new UserRequest(
            "John Doe",
            "john.doe@example.com",
            "password",
            "password",
            null, null, null));

    CreateResponse response = createTenant.execute(request);

    assertEquals("tenant-123", response.id());

  }

  @Test
  @DisplayName("Should be able to return AlreadyExistException when tenant slug already exists")
  public void shouldBeAbleToReturnAlreadyExistExceptionWhenTenantSlugAlreadyExists() {
    Tenant existingTenant = new Tenant();
    existingTenant.setId("tenant-123");
    existingTenant.setSlug("existing-slug");
    existingTenant.setDomain("existing-domain.com");

    when(tenantRepository.findBySlug(existingTenant.getSlug())).thenReturn(Optional.of(existingTenant));

    TenantRequest request = new TenantRequest(
        "existing Slug",
        "email@example.com", existingTenant.getDomain(), null);

    AlreadyExistException exception = assertThrows(
        AlreadyExistException.class,
        () -> createTenant.execute(request));

    assertEquals("tenant with this name already exists.", exception.getMessage());
  }

  @Test
  @DisplayName("Should be able to return AlreadyExistException when tenant domain already exists")
  public void shouldBeAbleToReturnAlreadyExistExceptionWhenTenantDomainAlreadyExists() {
    Tenant existingTenant = new Tenant();
    existingTenant.setId("tenant-123");
    existingTenant.setSlug("existing-slug");
    existingTenant.setDomain("existing-domain.com");

    when(tenantRepository.findBySlug(existingTenant.getSlug())).thenReturn(Optional.empty());
    when(tenantRepository.findByDomain("existing-domain.com")).thenReturn(Optional.of(existingTenant));

    TenantRequest request = new TenantRequest(
        "existing Slug",
        "email@example.com", "existing-domain.com", null);

    AlreadyExistException exception = assertThrows(
        AlreadyExistException.class,
        () -> createTenant.execute(request));

    assertEquals("tenant with this domain already exists.", exception.getMessage());
  }
}
