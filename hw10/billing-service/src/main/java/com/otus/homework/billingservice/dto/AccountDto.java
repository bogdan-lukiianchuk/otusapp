package com.otus.homework.billingservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AccountDto {
    @JsonProperty("accountId")
    private final Long accountId;
    @JsonProperty("userId")
    private final Long userId;
    @JsonProperty("money")
    private final Long money;
}
