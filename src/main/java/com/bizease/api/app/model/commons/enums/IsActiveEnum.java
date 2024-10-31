package com.bizease.api.app.model.commons.enums;

public enum IsActiveEnum {
    ACTIVE(0),
  INACTIVE(1);

  private final Integer isActive;

  IsActiveEnum(Integer isActive) {
    this.isActive = isActive;
  }

  public Integer getIsActive() {
    return this.isActive;
  }

  public static IsActiveEnum from(Integer isActive) {
    for (IsActiveEnum r : IsActiveEnum.values()) {
      if (r.getIsActive().equals(isActive)) {
        return r;
      }
    }
    throw new IllegalArgumentException("Invalid role: " + isActive);
  }
}
