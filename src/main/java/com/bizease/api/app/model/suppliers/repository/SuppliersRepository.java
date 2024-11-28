package com.bizease.api.app.model.suppliers.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.bizease.api.app.model.suppliers.entities.Suppliers;

public interface SuppliersRepository extends JpaRepository<Suppliers, Long>, JpaSpecificationExecutor<Suppliers> {
    
    Optional<Suppliers> findByCnpj(String cnpj);

    Optional<Suppliers> findByUuid(UUID uuid);

}
