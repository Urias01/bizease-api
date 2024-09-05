package com.bizease.api.app.model.categories.entities;

import com.bizease.api.app.model.commerce.entities.Commerce;
import com.bizease.api.app.model.commom.BaseModel;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "categories")
@Getter
@Setter
public class Categories extends BaseModel {

  private String name;
  private String description;

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

  public Categories(Categories categories) {
    this.name = categories.getName();
    this.description = categories.getDescription();
    this.commerce = categories.getCommerce();
  }

}
