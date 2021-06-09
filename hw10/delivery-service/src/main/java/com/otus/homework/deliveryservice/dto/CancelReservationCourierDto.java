package com.otus.homework.deliveryservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@ToString
@Getter
public class CancelReservationCourierDto {
    @JsonProperty("deliveryTime")
    private final LocalDateTime deliveryTime;
    @JsonProperty("courierName")
    private final String courierName;
    @NotNull
    @JsonProperty("orderId")
    private final Long orderId;

    public CancelReservationCourierDto(@JsonProperty("deliveryTime") LocalDateTime deliveryTime,
                                       @JsonProperty("courierName") String courierName,
                                       @JsonProperty("orderId") Long orderId) {
        this.deliveryTime = deliveryTime;
        this.courierName = courierName;
        this.orderId = orderId;
    }
}
