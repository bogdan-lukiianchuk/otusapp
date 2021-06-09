package com.otus.homework.orderservice.dto.rest;

import com.otus.homework.orderservice.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Getter
@AllArgsConstructor
public class OrderDto {
    private final Long id;
    private final Long userId;
    private final Instant createTime;
    private final Instant lastUpdateTime;
    private final OrderStatus status;
    private final List<ProductItemDto> items;
    private final Long totalCost;
    private final LocalDateTime deliveryTime;
    private final String courierName;
}
