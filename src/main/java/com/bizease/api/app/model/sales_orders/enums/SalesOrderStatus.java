package com.bizease.api.app.model.sales_orders.enums;

public enum SalesOrderStatus {

    PENDENTE("PENDENTE"),
    CONFIRMADO("CONFIRMADO"),
    ENVIADO("ENVIADO"),
    ENTREGUE("ENTREGUE"),
    CANCELADO("CANCELADO"),
    DEVOLVIDO("DEVOLVIDO");

    private final String salesOrderStatus;

    SalesOrderStatus(String salesOrderStatus) {
        this.salesOrderStatus = salesOrderStatus;
    }

    public String getSalesOrderStatus() {
        return this.salesOrderStatus;
    }

    public static SalesOrderStatus fromString(String salesOrderStatus) {
        for (SalesOrderStatus sos : SalesOrderStatus.values()) {
            if (sos.getSalesOrderStatus().equalsIgnoreCase(salesOrderStatus)){
                return sos;
            }
        }
        throw new IllegalArgumentException("Invalid Sale Order Status: " + salesOrderStatus);
    }

    public boolean canTransitionTo(SalesOrderStatus nextStatus) {
        switch (this) {
            case PENDENTE:
                return nextStatus == CONFIRMADO || nextStatus == CANCELADO;
            case CONFIRMADO:
                return nextStatus == ENVIADO || nextStatus == CANCELADO;
            case ENVIADO:
                return nextStatus == ENTREGUE || nextStatus == DEVOLVIDO;
            case ENTREGUE:
                return false;
            case CANCELADO:
            case DEVOLVIDO:
                return false;
            default:
                return false;
        }
    }

    public SalesOrderStatus transitionTo(SalesOrderStatus nextStatus) {
        if (canTransitionTo(nextStatus)) {
            return nextStatus;
        } else {
            throw new IllegalArgumentException("Não pode alterar de " + this + " para " + nextStatus);
        }
    }
}
