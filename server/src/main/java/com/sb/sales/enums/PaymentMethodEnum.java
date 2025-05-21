package com.sb.sales.enums;

public enum PaymentMethodEnum {
    CARTAO_CREDITO("Cartão de Crédito"),
    CARTAO_DEBITO("Cartão de Débito"),
    DINHEIRO("Dinheiro"),
    PIX("PIX"),
    BOLETO("Boleto"),
    TRANSFERENCIA("Transferência Bancária");

    private final String description;

    PaymentMethodEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
} 