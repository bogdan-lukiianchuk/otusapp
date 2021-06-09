package com.otus.homework.orderservice.dto.rest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class ProductDto {
    private Long id;
    private String name;
    private String category;
    private String brand;
    private int price;
}
