package com.otus.homework.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ListOrderResponse {
    private final List<OrderDto> orders;
}
