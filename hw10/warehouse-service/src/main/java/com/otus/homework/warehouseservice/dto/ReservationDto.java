package com.otus.homework.warehouseservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@AllArgsConstructor
@Getter
@ToString
public class ReservationDto {
    @JsonProperty("orderId")
    private final long orderId;
    @JsonProperty("reservations")
    private final List<SupplyDto> reservations;
}
