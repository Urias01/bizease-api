package com.bizease.api.app.model.commerce.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bizease.api.app.model.commerce.entities.Commerce;

public interface CommerceRepository  extends JpaRepository<Commerce, Long> {
  Optional<Commerce> findByCnpj(String cnpj);
  Optional<Commerce> findByName(String name);
}
