package com.bizease.api.app.models.entities;

import com.bizease.api.app.models.commons.Auditable;
import com.bizease.api.app.models.enums.AccessProfile;

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
@Table(name = "permissions")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Permission extends Auditable {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;
  private AccessProfile accessLevel;
  @ManyToOne(targetEntity = View.class)
  @JoinColumn(name = "view_id", referencedColumnName = "id")
  private View view;
  @JoinColumn(name = "access_group_id", referencedColumnName = "id")
  @ManyToOne(targetEntity = AccessGroup.class)
  private AccessGroup accessGroup;
}
