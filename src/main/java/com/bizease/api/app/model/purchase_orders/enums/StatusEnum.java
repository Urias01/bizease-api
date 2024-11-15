package com.bizease.api.app.model.purchase_orders.enums;

public enum StatusEnum {

    REALIZADO("REALIZADO"),
    CONFIRMADO("CONFIRMADO"),
    RECEBIDO("RECEBIDO"),
    CANCELADO("CANCELADO");

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

    public boolean canTransitionTo(StatusEnum nextStatus) {
        switch (this) {
            case REALIZADO:
                return nextStatus == CONFIRMADO || nextStatus == CANCELADO;
            case CONFIRMADO:
                return nextStatus == RECEBIDO || nextStatus == CANCELADO;
            case RECEBIDO:
                return false;
            case CANCELADO:
            default:
                return false;
        }
    }

    public StatusEnum transitionTo(StatusEnum nextStatus) {
        if (canTransitionTo(nextStatus)) {
            return nextStatus;
        } else {
            throw new IllegalArgumentException("Não pode alterar de " + this + " para " + nextStatus);
        }
    }
}
