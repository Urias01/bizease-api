package com.bizease.api.app.model.user.enums;

public enum ActiveUserEnum {

  ACTIVE(0),
  INACTIVE(1);

  private final Integer isActive;

  ActiveUserEnum(Integer isActive) {
    this.isActive = isActive;
  }

  public Integer getIsActive() {
    return this.isActive;
  }

  public static ActiveUserEnum from(Integer isActive) {
    for (ActiveUserEnum r : ActiveUserEnum.values()) {
      if (r.getIsActive().equals(isActive)) {
        return r;
      }
    }
    throw new IllegalArgumentException("Invalid role: " + isActive);
  }
}
