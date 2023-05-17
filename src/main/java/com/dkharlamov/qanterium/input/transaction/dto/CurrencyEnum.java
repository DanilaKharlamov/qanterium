package com.dkharlamov.qanterium.input.transaction.dto;

public enum CurrencyEnum {
    USD("US Dollar"),
    EUR("Euro"),
    RU("Russian Rouble"),
    GEL("Georgian Lari");

    private final String currency;

    CurrencyEnum(String currency) {
        this.currency = currency;
    }

    public String getPrefix() {
        return currency;
    }
}
