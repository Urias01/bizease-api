package com.bizease.api.app.model.categories.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.validator.constraints.Length;

import com.bizease.api.app.model.categories.dto.CategoriesDTO;
import com.bizease.api.app.model.commerce.entities.Commerce;
import com.bizease.api.app.model.commons.enums.IsActiveEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "categories")
@Getter
@Setter
public class Categories {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @UuidGenerator()
  @Column(unique = true, nullable = false)
  private UUID uuid;

  @Column(nullable = false)
  @Length(max = 100)
  private String name;
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

  public Categories() {
  }

  public Categories(String name, String description, Commerce commerce) {
    this.name = name;
    this.description = description;
    this.commerce = commerce;
  }

  public Categories(CategoriesDTO categories, Commerce commerce) {
    this.name = categories.getName();
    this.description = categories.getDescription();
    this.isActive =  categories.getIsActive() != null ? IsActiveEnum.from(categories  .getIsActive()) : IsActiveEnum.ACTIVE;
    this.commerce = commerce;
  }

}
