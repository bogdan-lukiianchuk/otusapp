package com.otus.homework.orderservice.dto.rest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class ProductItemDto {
    private Long productId;
    private String name;
    private int count;
    private int price;
    private int cost;
}
