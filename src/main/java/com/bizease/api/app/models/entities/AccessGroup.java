package com.bizease.api.app.models.entities;

import java.util.Set;

import com.bizease.api.app.models.commons.Auditable;
import com.bizease.api.app.models.enums.AccessGroupStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "access_groups")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AccessGroup extends Auditable {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;
  private String name;
  private String description;
  private AccessGroupStatus status;
  @ManyToOne
  @JoinColumn(name = "tenant_id", referencedColumnName = "id")
  private Tenant tenant;
  @OneToMany(mappedBy = "accessGroup")
  private Set<Permission> permissions;
}
