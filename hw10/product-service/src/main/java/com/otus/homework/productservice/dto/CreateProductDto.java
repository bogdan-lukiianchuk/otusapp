package com.otus.homework.productservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.Instant;

@AllArgsConstructor
@Getter
@ToString
public class CreateProductDto {
    private final String name;
    private final String category;
    private final String brand;
    private final int price;
    private final Instant availabilityDate;
}
