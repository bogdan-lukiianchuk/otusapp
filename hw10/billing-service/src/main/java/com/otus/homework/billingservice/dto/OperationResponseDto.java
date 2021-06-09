package com.otus.homework.billingservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.otus.homework.billingservice.enums.OperationStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OperationResponseDto {
    @JsonProperty("message")
    private final String message;
    @JsonProperty("status")
    private final OperationStatus status;
}
