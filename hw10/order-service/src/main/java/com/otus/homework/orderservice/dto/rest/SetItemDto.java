package com.otus.homework.orderservice.dto.rest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SetItemDto {
    private String name;
    private int count;
}
