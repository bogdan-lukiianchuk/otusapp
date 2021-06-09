package com.otus.homework.billingservice.controllers;

import com.otus.homework.billingservice.dto.CancelDto;
import com.otus.homework.billingservice.dto.OperationDto;
import com.otus.homework.billingservice.dto.OperationResponseDto;
import com.otus.homework.billingservice.services.OperationResponseDtoMapper;
import com.otus.homework.billingservice.services.OperationService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Api(tags = {"Operation"})
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/accounts/operations")
public class OperationController {
    private final OperationService operationService;
    private final OperationResponseDtoMapper operationResponseDtoMapper;

    @PreAuthorize("hasRole('ROLE_ORDER_SRV')")
    @PostMapping(path = "/cancel")
    public OperationResponseDto cancel(@RequestBody @Valid CancelDto cancelDto) {
        return operationResponseDtoMapper.toDto(operationService.cancel(cancelDto));
    }

    @PostMapping()
    public OperationResponseDto execute(@RequestBody OperationDto operationDto) {
        return operationResponseDtoMapper.toDto(operationService.execute(operationDto));
    }
}
