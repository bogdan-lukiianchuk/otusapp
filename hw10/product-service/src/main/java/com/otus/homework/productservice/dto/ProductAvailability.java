package com.otus.homework.productservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.Instant;

@AllArgsConstructor
@Getter
@ToString
public class ProductAvailability {
    private final Long id;
    private final Instant availabilityDate;
}
