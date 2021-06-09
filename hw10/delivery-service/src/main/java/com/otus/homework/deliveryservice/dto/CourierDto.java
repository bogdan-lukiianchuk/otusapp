package com.otus.homework.deliveryservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CourierDto {
    @JsonProperty("name")
    private final String name;
}
