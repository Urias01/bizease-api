package com.bizease.api.app.model.suppliers.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bizease.api.app.model.suppliers.entities.Suppliers;

public interface SuppliersRepository extends JpaRepository<Suppliers, Long> {
    
    Optional<Suppliers> findByCnpj(String cnpj);
}
