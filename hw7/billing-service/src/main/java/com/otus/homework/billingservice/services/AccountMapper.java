package com.otus.homework.billingservice.services;

import com.otus.homework.billingservice.dto.AccountDto;
import com.otus.homework.billingservice.entities.Account;
import org.springframework.stereotype.Service;

@Service
public class AccountMapper {

    public AccountDto toDto(Account entity){
        if (entity == null){
            return null;
        }
        return new AccountDto(entity.getAccountId(), entity.getUserId(), entity.getMoney());
    }
}
