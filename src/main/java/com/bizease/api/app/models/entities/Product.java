package com.bizease.api.app.models.entities;

import java.math.BigDecimal;

import com.bizease.api.app.models.commons.Auditable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "products")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Product extends Auditable {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;
  private String name;
  private String description;
  private String barcode;
  private BigDecimal price;
  @ManyToOne(targetEntity = Tenant.class)
  @JoinColumn(name = "tenant_id", referencedColumnName = "id")
  private Tenant tenant;
  @ManyToOne(optional = false)
  @JoinColumn(name = "category_id", referencedColumnName = "id")
  private Category category;
}