package com.otus.homework.billingservice.services;

import com.otus.homework.billingservice.dto.OperationResponseDto;
import com.otus.homework.billingservice.model.OperationResponse;
import org.springframework.stereotype.Service;

@Service
public class OperationResponseDtoMapper {

    public OperationResponseDto toDto(OperationResponse response) {
        return new OperationResponseDto(response.getMessage(), response.getStatus());
    }
}
