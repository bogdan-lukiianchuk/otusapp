package com.otus.homework.orderservice.model;

import com.otus.homework.orderservice.interfaces.JsonData;
import lombok.Getter;

@Getter
public class EmptyJson implements JsonData {
    public static final String TYPE = "empty";
}
