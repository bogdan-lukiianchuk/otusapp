package com.otus.homework.orderservice.dto.rest.template;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.otus.homework.orderservice.enums.OperationStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class OperationResponseDto {
    @JsonProperty("message")
    private String message;
    @JsonProperty("status")
    private OperationStatus status;
}
