package com.otus.homework.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderCreatedDto {
    private final Long orderId;
}
