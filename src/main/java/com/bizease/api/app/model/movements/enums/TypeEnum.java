package com.bizease.api.app.model.movements.enums;

public enum TypeEnum {

    ENTRADA("ENTRADA"),
    SAIDA("SAIDA"),
    PERDA("PERDA"),
    DEVOLUCAO("DEVOLUCAO"),
    AJUSTE("AJUSTE"),
    TRANSFERENCIA("TRANSFERENCIA");

    private final String type;

    TypeEnum(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }

    public static TypeEnum fromString(String type) {
        for (TypeEnum t : TypeEnum.values()) {
            if (t.getType().equalsIgnoreCase(type)) {
                return t;
            }
        }
        throw new IllegalArgumentException("Invalid type: " + type);
    }
}
