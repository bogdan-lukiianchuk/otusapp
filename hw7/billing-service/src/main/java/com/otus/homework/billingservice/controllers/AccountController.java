package com.otus.homework.billingservice.controllers;

import com.otus.homework.billingservice.dto.*;
import com.otus.homework.billingservice.services.AccountMapper;
import com.otus.homework.billingservice.services.AccountService;
import com.otus.homework.billingservice.services.OperationResponseDtoMapper;
import com.otus.homework.billingservice.services.OperationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/accounts")
public class AccountController {
    private final AccountService accountService;
    private final AccountMapper accountMapper;
    private final OperationService operationService;
    private final OperationResponseDtoMapper operationResponseDtoMapper;

    @PostMapping("/find")
    public AccountDto getAccountById(@RequestBody(required = false) GetAccountDto getAccountDto) {
        return accountMapper.toDto(accountService.getUserAccount(getAccountDto));
    }

    @PostMapping("/create")
    public AccountDto create() {
        return accountMapper.toDto(accountService.create());
    }

    @PreAuthorize("hasRole('ROLE_SRV')")
    @PostMapping(path = "/createSrv")
    public AccountDto createUser(@RequestBody @Valid UserDto user) {
        return accountMapper.toDto(accountService.create(user.getUserId()));
    }

    @PostMapping("/operations")
    public OperationResponseDto execute(@RequestBody OperationDto operationDto) {
        return operationResponseDtoMapper.toDto(operationService.execute(operationDto));
    }
}
