package com.otus.homework.orderservice.dto.rest.billing;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class CancelDto {
    @JsonProperty("operationUuid")
    private final UUID operationUuid;
    @JsonProperty("cancelOperationUuid")
    private final UUID cancelOperationUuid;
    @JsonProperty("accountId")
    private final Long accountId;
}
