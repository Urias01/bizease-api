package com.bizease.api.app.models.enums;

public enum AccessGroupStatus {
  ACTIVE("ACTIVE"),
  INACTIVE("INACTIVE"),
  PENDING("PENDING"),
  SUSPENDED("SUSPENDED"),
  DELETED("DELETED");

  private String description;

  AccessGroupStatus(String description) {
    this.description = description;
  }

  public String getDescription() {
    return description;
  }
}
