package com.otus.homework.orderservice.dto.rest.delivery;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@ToString
@Getter
public class CancelReservationCourierDto {
    @JsonProperty("deliveryTime")
    private final LocalDateTime deliveryTime;
    @JsonProperty("courierName")
    private final String courierName;
    @JsonProperty("orderId")
    private final Long orderId;
}
