package com.otus.homework.warehouseservice.enums;

public enum OrderStatus {
    CREATED("создан"),
    CANCELED("отменен"),
    DONE("выполнен"),
    EXPIRED("истек");

    private final String value;

    OrderStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
