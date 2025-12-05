package com.bizease.api.app.infrastructure.persistence.jpa.accessGroup;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bizease.api.app.models.entities.AccessGroup;

public interface AccessGroupRepository extends JpaRepository<AccessGroup, String> {
  
}
