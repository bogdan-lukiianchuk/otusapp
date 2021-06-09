package com.otus.homework.orderservice.dto.rest.delivery;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourierDto {
    @JsonProperty("name")
    private String name;
}
