package com.bizease.api.app.models.entities;

import java.time.LocalDateTime;
import java.util.Set;

import com.bizease.api.app.models.commons.Auditable;
import com.bizease.api.app.models.enums.ActivationsState;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "views")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class View extends Auditable {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;
  private String name;
  private String route;
  private String icon;
  @Column(name = "sort_order")
  private Integer order;
  private ActivationsState status;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
  @OneToMany(mappedBy = "view")
  private Set<Permission> permissions;

}
