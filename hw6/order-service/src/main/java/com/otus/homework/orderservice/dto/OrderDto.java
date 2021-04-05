package com.otus.homework.orderservice.dto;

import com.otus.homework.orderservice.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;
import java.util.List;

@Getter
@AllArgsConstructor
public class OrderDto {
    private final Long id;
    private final Long userId;
    private final Instant createTime;
    private final Instant lastUpdateTime;
    private final OrderStatus status;
    private final List<ProductDto> items;
    private final Long totalCost;
}
