package com.bizease.api.app.model.products.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;

import com.bizease.api.app.model.categories.entities.Categories;
import com.bizease.api.app.model.commerce.entities.Commerce;
import com.bizease.api.app.model.commons.enums.IsActiveEnum;
import com.bizease.api.app.model.products.dto.ProductsDTO;

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
    private String location;
    private LocalDate expirationDate;
    private String description;
    
    @Column(nullable = false, name = "is_active")
    private IsActiveEnum isActive;
    
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "com_id", referencedColumnName = "id")
    private Commerce commerce;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cat_id", referencedColumnName = "id")
    private Categories categories;

    public Products (ProductsDTO productsDTO, Commerce commerce, Categories categories) {
        this.name = productsDTO.getName();
        this.unit = productsDTO.getUnit();
        this.minimumStock = productsDTO.getMinimumStock();
        this.location = productsDTO.getLocation();
        this.expirationDate = productsDTO.getExpirationDate();
        this.description = productsDTO.getDescription();
        this.isActive =  productsDTO.getIsActive() != null ? IsActiveEnum.from(productsDTO.getIsActive()) : IsActiveEnum.ACTIVE;
        this.createdAt = productsDTO.getCreatedAt();
        this.updatedAt = productsDTO.getUpdatedAt();
        this.commerce = commerce;
        this.categories = categories;
    }

}
