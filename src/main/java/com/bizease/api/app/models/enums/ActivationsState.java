package com.bizease.api.app.models.enums;

public enum ActivationsState {
  ACTIVE("ACTIVE"),
  INACTIVE("INACTIVE"),
  SUSPENDED("SUSPENDED"),
  ARCHIVED("ARCHIVED");

  private String description;

  ActivationsState(String description) {
    this.description = description;
  }

  public String getDescription() {
    return description;
  }
}
