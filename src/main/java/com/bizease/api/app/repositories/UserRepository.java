package com.bizease.api.app.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bizease.api.app.models.User;


@Repository
public interface UserRepository extends JpaRepository<User, String> {

  Optional<User> findByEmailAndTenantId(String email, String tenantId);
  Optional<User> findByIdAndTenantId(String id, String tenantId);

}
