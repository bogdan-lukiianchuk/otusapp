package com.otus.homework.warehouseservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.otus.homework.warehouseservice.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
@AllArgsConstructor
public class ReserveOrderDto {
    @JsonProperty("orderId")
    private final Long id;
    @JsonProperty("status")
    private final OrderStatus status;
    @JsonProperty("reservations")
    private final List<SupplyDto> reservations;
    @JsonProperty("createdTime")
    private final Instant createdTime;
    @JsonProperty("reservationEndTime")
    private final Instant reservationEndTime;
}
