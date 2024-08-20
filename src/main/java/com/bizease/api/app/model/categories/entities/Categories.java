package com.bizease.api.app.model.categories.entities;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;

import com.bizease.api.app.model.categories.dto.CategoriesDTO;
import com.bizease.api.app.model.commerce.entities.Commerce;
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

  @UuidGenerator
  private String uuid;
  private String name;
  private String description;
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
    this.commerce = commerce;
  }

}
