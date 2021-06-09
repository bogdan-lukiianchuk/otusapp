package com.otus.homework.deliveryservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@ToString
@Getter
public class ReserveCourierDto {
    @NotNull
    @JsonProperty("deliveryTime")
    private final LocalDateTime deliveryTime;
    @NotNull
    @JsonProperty("orderId")
    private final Long orderId;

    public ReserveCourierDto(@JsonProperty("deliveryTime") LocalDateTime deliveryTime,
                             @JsonProperty("orderId") Long orderId) {
        this.deliveryTime = deliveryTime;
        this.orderId = orderId;
    }
}
