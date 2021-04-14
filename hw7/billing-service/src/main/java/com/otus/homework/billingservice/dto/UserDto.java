package com.otus.homework.billingservice.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UserDto {
    @NotNull
    private final Long userId;

    @JsonCreator
    public UserDto(@JsonProperty("userId") Long userId) {
        this.userId = userId;
    }
}
