package com.otus.homework.orderservice.model;

import com.otus.homework.orderservice.enums.OrderStatus;
import com.otus.homework.orderservice.interfaces.JsonData;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UpdateOrderJson implements JsonData {
    public static final String TYPE = "update";
    private OrderStatus status;
}
