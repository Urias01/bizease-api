package com.bizease.api.app.model.commerce.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.validator.constraints.Length;

import com.bizease.api.app.model.commons.enums.IsActiveEnum;

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
  @Column(unique = true, nullable = false)
  private UUID uuid;

  @Column(nullable = false)
  @Length(max = 14)
  private String cnpj;

  @Column(nullable = false)
  @Length(max = 100)
  private String name;

  private String address;

  @Length(max = 6)
  private String addressNumber;

  @Length(max = 100)
  private String neighborhood;

  @Length(max = 100)
  private String city;

  @Length(max = 2)
  private String uf;

  @Length(max = 8)
  private String postalCode;

  @Column(nullable = false)
  @Length(max = 11)
  private String phoneNumber;
  
  @Column(nullable = false, name = "is_active")
  private IsActiveEnum isActive;

  @CreationTimestamp
  private LocalDateTime createdAt;
  @UpdateTimestamp
  private LocalDateTime updatedAt;

  public Commerce() {
  }

  public Commerce(String cnpj, String name, String postalCode) {
    this.cnpj = cnpj;
    this.name = name;
    this.postalCode = postalCode;
  }

}
