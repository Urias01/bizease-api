package com.bizease.api.app.model.commerce.entities;

import java.time.LocalDateTime;

import com.bizease.api.app.model.commons.enums.IsActiveEnum;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "commerces")
@Getter
@Setter
public class Commerce {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @UuidGenerator()
  @Column(unique = true)
  private String uuid;

  private String cnpj;
  private String name;
  private String phoneNumber;

  private String postalCode;
  private String address;
  private String addressNumber;
  private String neighborhood;
  private String city;
  private String uf;
  @Column(nullable = false, name = "is_active")
  private IsActiveEnum isActive;

  @CreationTimestamp
  private LocalDateTime createdAt;
  @UpdateTimestamp
  private LocalDateTime updatedAt;

  public Commerce() {}

  public Commerce(String cnpj, String name, String postalCode) {
    this.cnpj = cnpj;
    this.name = name;
    this.postalCode = postalCode;
  }

}
