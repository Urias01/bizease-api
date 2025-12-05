package com.bizease.api.app.models.entities;

import java.time.LocalDateTime;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tenants")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tenant {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;
  private String name;
  private String slug;
  private String email;
  private String domain;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
  @OneToMany(mappedBy = "tenant")
  private Set<Product> products;
  @OneToMany(mappedBy = "tenant")
  private Set<User> users;
  @OneToMany(mappedBy = "tenant")
  private Set<AccessGroup> accessGroups;

}