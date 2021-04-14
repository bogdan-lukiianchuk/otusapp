package com.otus.homework.orderservice.dto.rest;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderCreatedDto {
    private final Long orderId;
}
