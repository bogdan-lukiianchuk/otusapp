package com.otus.homework.orderservice.model;

import com.otus.homework.orderservice.interfaces.JsonData;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SetItemJson implements JsonData {
    public static final String TYPE = "setitem";
    private String name;
    private int count;
}
