package com.bizease.api.app.model.user.enums;

public enum RoleEnum {
  
  ADMIN("ADMIN"),
  OWNER("OWNER"),
  FINANCE("FINANCE"),
  EMPLOYEE("EMPLOYEE");

  private final String role;

  RoleEnum(String role) {
    this.role = role;
  }

  public String getRole() {
    return this.role;
  }

  public static RoleEnum fromString(String role) {
    for (RoleEnum r : RoleEnum.values()) {
      if (r.getRole().equalsIgnoreCase(role)) {
        return r;
      }
    }
    throw new IllegalArgumentException("Invalid role: " + role);
  }
}
