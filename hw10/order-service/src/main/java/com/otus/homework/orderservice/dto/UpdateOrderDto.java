package com.otus.homework.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UpdateOrderDto {
    private LocalDateTime deliveryTime;
    private String courierName;

    public UpdateOrderDto(LocalDateTime deliveryTime) {
        this.deliveryTime = deliveryTime;
    }
}
