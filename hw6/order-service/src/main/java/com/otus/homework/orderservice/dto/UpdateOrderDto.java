package com.otus.homework.orderservice.dto;

import com.otus.homework.orderservice.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UpdateOrderDto {
    private OrderStatus status;
}
