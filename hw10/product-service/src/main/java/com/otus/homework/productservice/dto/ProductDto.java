package com.otus.homework.productservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class ProductDto {
    private final Long id;
    private final String name;
    private final String category;
    private final String brand;
    private final int price;
}
