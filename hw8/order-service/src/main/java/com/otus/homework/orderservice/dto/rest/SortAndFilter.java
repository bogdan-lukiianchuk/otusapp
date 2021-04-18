package com.otus.homework.orderservice.dto.rest;

import com.otus.homework.orderservice.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;

@AllArgsConstructor
@Getter
public class SortAndFilter {
    private final String sortBy;
    private final Instant createdTime;
    private final Instant createdPeriodStart;
    private final Instant createdPeriodEnd;
    private final String productName;
    private final OrderStatus status;
}
