package com.bizease.api.app.model.purchase_orders.enums;

public enum StatusEnum {

    REALIZADO("REALIZADO"),
    CONFIRMADO("CONFIRMADO"),
    RECEBIDO("RECEBIDO");

    private final String status;

    StatusEnum(String status) {
        this.status = status;
    }

    public String getStatus() {
        return this.status;
    }

    public static StatusEnum fromString(String status) {
        for (StatusEnum s : StatusEnum.values()) {
            if (s.getStatus().equalsIgnoreCase(status)) {
                return s;
            }
        }
        throw new IllegalArgumentException("Invalid status: " + status);
    }
}
