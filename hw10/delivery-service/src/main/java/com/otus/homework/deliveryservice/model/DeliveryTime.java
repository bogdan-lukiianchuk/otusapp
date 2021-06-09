package com.otus.homework.deliveryservice.model;

import com.otus.homework.deliveryservice.enuns.Status;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class DeliveryTime {
    private final Integer hour;
    private Long orderId;
    private Status status;

    public DeliveryTime(Integer hour, Status status) {
        this.hour = hour;
        this.status = status;
    }
}
