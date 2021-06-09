package com.otus.homework.deliveryservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Courier {
    private String name;
    private List<DeliveryTime> workTimes;
}
