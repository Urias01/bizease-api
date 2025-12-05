package com.bizease.api.app.infrastructure.persistence.jpa.tenant;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bizease.api.app.models.entities.Tenant;

@Repository
public interface TenantRepository extends JpaRepository<Tenant, String> {

  Optional<Tenant> findBySlug(String slug); 
  Optional<Tenant> findByDomain(String domain);
}
