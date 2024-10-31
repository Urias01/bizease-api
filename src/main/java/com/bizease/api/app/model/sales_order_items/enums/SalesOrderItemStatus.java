package com.bizease.api.app.model.sales_order_items.enums;

public enum SalesOrderItemStatus {

    DISPONIVEL("DISPONÍVEL"),
    FORA_DE_ESTOQUE("FORA_DE_ESTOQUE"),
    RESERVADO("RESERVADO"),
    ENVIADO("ENVIADO"),
    DEVOLVIDO("DEVOLVIDO"),
    DANIFICADO("DANIFICADO");

    private final String salesOrderItemStatus;

    SalesOrderItemStatus(String salesOrderItemStatus) {
        this.salesOrderItemStatus = salesOrderItemStatus;
    }

    public String getSalesOrderItemStatus() {
        return this.salesOrderItemStatus;
    }

    public static SalesOrderItemStatus fromString(String salesOrderItemStatus) {
        for (SalesOrderItemStatus sois: SalesOrderItemStatus.values()) {
            if (sois.getSalesOrderItemStatus().equalsIgnoreCase(salesOrderItemStatus)) {
                return sois;
            }
        }
        throw new IllegalArgumentException("Invalid Sales Order Item Status: " + salesOrderItemStatus);
    }
}
