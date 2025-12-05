package com.bizease.api.app.infrastructure.persistence.jpa.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bizease.api.app.models.entities.User;


public interface UserRepository extends JpaRepository<User, String> {

  Optional<User> findByEmail(String email);
  Optional<User> findByEmailAndTenantId(String email, String tenantId);
  Optional<User> findByIdAndTenantId(String id, String tenantId);
  Optional<User> findById(String id);

}
