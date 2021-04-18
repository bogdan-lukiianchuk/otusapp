package com.otus.homework.orderservice.enums;

import com.otus.homework.orderservice.exceptions.ApplicationException;

public enum OrderSort {
    CREATE_TIME("createTime"),
    COST("totalCost");

    private final String value;

    OrderSort(String value) {
        this.value = value;
    }

    public static OrderSort getBuValue(String value) {
        for (OrderSort orderSort : values()) {
            if (value.equalsIgnoreCase(orderSort.value)) {
                return orderSort;
            }
        }
        throw new ApplicationException("Нейзвестное поле для сортировки");
    }
}
