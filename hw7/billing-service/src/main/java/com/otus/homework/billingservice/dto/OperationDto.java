package com.otus.homework.billingservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class OperationDto {
    @NotNull
    @JsonProperty("operationCode")
    private final String operationCode;
    @NotNull
    @JsonProperty("operationUuid")
    private final UUID operationUuid;
    @NotNull
    @JsonProperty("accountId")
    private final Long accountId;
    @NotNull
    @JsonProperty("money")
    private final Long money;
}
