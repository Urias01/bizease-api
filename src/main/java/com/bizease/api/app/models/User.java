package com.bizease.api.app.models;

import com.bizease.api.app.models.enums.AccessProfile;
import com.bizease.api.app.models.enums.AccessStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;
  private String name;
  private String email;
  private String password;
  @Enumerated(EnumType.STRING)
  private AccessProfile type;
  @Enumerated(EnumType.STRING)
  private AccessStatus status;
  @ManyToOne(targetEntity = Tenant.class)
  @JoinColumn(name = "tenant_id", referencedColumnName = "id")
  private Tenant tenant;

}
