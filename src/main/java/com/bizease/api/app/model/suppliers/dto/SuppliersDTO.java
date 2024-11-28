package com.bizease.api.app.model.suppliers.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class SuppliersDTO {
    
    private Long id;
    private UUID uuid;
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
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private String commerceUuid;
}
