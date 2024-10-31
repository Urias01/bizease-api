package com.bizease.api.app.model.sales_orders.enums;

public enum SalesOrderStatus {

    PENDENTE("PENDENTE"),
    CONFIRMADO("CONFIRMADO"),
    ENVIADO("ENVIADO"),
    ENTREGUE("ENTREGUE"),
    CANCELADO("CANCELADO"),
    RETORNADO("RETORNADO");

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
}
