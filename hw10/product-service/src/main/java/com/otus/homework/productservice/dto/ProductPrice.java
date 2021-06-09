package com.otus.homework.productservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class ProductPrice {
    private final Long id;
    private final Integer price;
}
