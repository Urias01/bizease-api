package com.bizease.api.app.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bizease.api.app.models.Tenant;

@Repository
public interface TenantRepository extends JpaRepository<Tenant, String> {

  Optional<Tenant> findBySlug(String slug); 
  Optional<Tenant> findByDomain(String domain);
}
