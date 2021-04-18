package com.otus.homework.billingservice.model;

import com.otus.homework.billingservice.enums.OperationStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class OperationResponse {
    private final String message;
    private final OperationStatus status;
    private Long money;

    public OperationResponse(String message, OperationStatus status) {
        this.message = message;
        this.status = status;
    }
}
