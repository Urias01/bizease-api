package com.bizease.api.app.infrastructure.persistence.jpa.permission;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bizease.api.app.models.entities.Permission;

public interface PermissionRepository extends JpaRepository<Permission, String> {
  
}
