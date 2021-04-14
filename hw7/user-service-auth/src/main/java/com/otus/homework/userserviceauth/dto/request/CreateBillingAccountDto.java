package com.otus.homework.userserviceauth.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CreateBillingAccountDto {
    @NotNull
    private final Long userId;

    @JsonCreator
    public CreateBillingAccountDto(@JsonProperty("userId") Long userId) {
        this.userId = userId;
    }
}
