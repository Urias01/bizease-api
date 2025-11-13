package com.bizease.api.app.models.enums;

public enum AccessStatus {
  ACTIVE("ACTIVE"),
  INACTIVE("INACTIVE"),
  SUSPENDED("SUSPENDED");

  private String description;

  AccessStatus(String description) {
    this.description = description;
  }

  public String getDescription() {
    return description;
  }
}
