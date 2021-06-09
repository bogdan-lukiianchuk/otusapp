package com.otus.homework.billingservice.model;

import com.otus.homework.billingservice.enums.OperationStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
@Getter
public class OperationResponse {
    private final String message;
    private final OperationStatus status;
    private UUID operationId;
    private Long money;

    public OperationResponse(String message, OperationStatus status) {
        this.message = message;
        this.status = status;
    }

    public OperationResponse(String message, OperationStatus status, Long money) {
        this.message = message;
        this.status = status;
        this.money = money;
    }
}
