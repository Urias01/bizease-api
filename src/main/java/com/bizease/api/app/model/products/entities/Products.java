package com.bizease.api.app.model.products.entities;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;

import com.bizease.api.app.model.products.dto.ProductsDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "products")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Products {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @UuidGenerator
    private String uuid;

    private String name;
    private Integer unit;
    private Integer minimumStock;
    private String description;
    
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public Products (ProductsDTO productsDTO) {
        this.name = productsDTO.getName();
        this.unit = productsDTO.getUnit();
        this.minimumStock = productsDTO.getMinimumStock();
        this.description = productsDTO.getDescription();
        this.createdAt = productsDTO.getCreatedAt();
        this.updatedAt = productsDTO.getUpdatedAt();
    }

}
