package com.otus.homework.userserviceauth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto {
    @JsonProperty("accountId")
    private Long accountId;
    @JsonProperty("userId")
    private Long userId;
    @JsonProperty("money")
    private Long money;
}
