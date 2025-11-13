package com.bizease.api.app.models.enums;

public enum AccessProfile {
  ADMIN("ADMIN"),
  MANAGER("MANAGER"),
  FINANCIAL("FINANCIAL"),
  REPLENISHER("REPLENISHER"),
  BUYER("BUYER"),
  INSPECTOR("INSPECTOR"),
  USER("USER");

  private String description;

  AccessProfile(String description) {
    this.description = description;
  }

  public String getDescription() {
    return description;
  }
}
