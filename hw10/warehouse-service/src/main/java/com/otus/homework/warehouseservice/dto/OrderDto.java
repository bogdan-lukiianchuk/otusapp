package com.otus.homework.warehouseservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class OrderDto {
    @JsonProperty("orderId")
    private final long orderId;

    public OrderDto(@JsonProperty("orderId") long orderId) {
        this.orderId = orderId;
    }
}
