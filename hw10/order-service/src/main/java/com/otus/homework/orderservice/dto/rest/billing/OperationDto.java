package com.otus.homework.orderservice.dto.rest.billing;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OperationDto {
    @JsonProperty("operationCode")
    private String operationCode;
    @JsonProperty("operationUuid")
    private UUID operationUuid;
    @JsonProperty("accountId")
    private Long accountId;
    @JsonProperty("money")
    private Long money;
}
