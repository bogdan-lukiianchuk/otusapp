package com.otus.homework.billingservice.controllers;

import com.otus.homework.billingservice.dto.AccountDto;
import com.otus.homework.billingservice.dto.GetAccountDto;
import com.otus.homework.billingservice.dto.UserDto;
import com.otus.homework.billingservice.services.AccountMapper;
import com.otus.homework.billingservice.services.AccountService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Api(tags = {"Account"})
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/accounts")
public class AccountController {
    private final AccountService accountService;
    private final AccountMapper accountMapper;

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
}
