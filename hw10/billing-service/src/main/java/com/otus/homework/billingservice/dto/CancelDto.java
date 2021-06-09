package com.otus.homework.billingservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class CancelDto {
    @NotNull
    @JsonProperty("operationUuid")
    private final UUID operationUuid;
    @NotNull
    @JsonProperty("cancelOperationUuid")
    private final UUID cancelOperationUuid;
    @NotNull
    @JsonProperty("accountId")
    private final Long accountId;
}
