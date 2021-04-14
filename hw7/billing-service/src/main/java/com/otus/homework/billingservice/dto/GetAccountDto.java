package com.otus.homework.billingservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetAccountDto {
    @JsonProperty("userId")
    private final Long userId;
    @JsonProperty("accountId")
    private final Long accountId;
}
