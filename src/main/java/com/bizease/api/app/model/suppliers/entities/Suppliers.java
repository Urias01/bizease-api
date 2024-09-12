package com.bizease.api.app.model.suppliers.entities;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;

import com.bizease.api.app.model.commerce.entities.Commerce;
import com.bizease.api.app.model.suppliers.dto.SuppliersDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "suppliers")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Suppliers {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @UuidGenerator
    private String uuid;

    @Column(unique = true)
    private String cnpj;
    private String name;
    private String address;
    private String addressNumber;
    private String neighborhood;
    private String city;
    private String uf;
    private String postalCode;
    private String category;
    private String phoneNumber;
    private String email;
    
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "com_id", referencedColumnName = "id")
    private Commerce commerce;

    public Suppliers(SuppliersDTO suppliersDTO, Commerce commerce) {
        this.cnpj = suppliersDTO.getCnpj();
        this.name = suppliersDTO.getName();
        this.address = suppliersDTO.getAddress();
        this.addressNumber = suppliersDTO.getAddressNumber();
        this.neighborhood = suppliersDTO.getNeighborhood();
        this.city = suppliersDTO.getCity();
        this.uf = suppliersDTO.getUf();
        this.postalCode = suppliersDTO.getPostalCode();
        this.category = suppliersDTO.getCategory();
        this.phoneNumber = suppliersDTO.getPhoneNumber();
        this.email = suppliersDTO.getEmail();
        this.createdAt = suppliersDTO.getCreatedAt();
        this.updatedAt = suppliersDTO.getUpdatedAt();
        this.commerce = commerce;
    }
    
}
