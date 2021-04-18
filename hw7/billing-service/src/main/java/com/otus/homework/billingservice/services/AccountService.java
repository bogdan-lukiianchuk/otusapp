package com.otus.homework.billingservice.services;

import com.otus.homework.billingservice.dto.GetAccountDto;
import com.otus.homework.billingservice.entities.Account;
import com.otus.homework.billingservice.repositories.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final UserSessionService userSessionService;

    @Transactional(readOnly = true)
    public Account getUserAccount(GetAccountDto getAccountDto) {
        if (userSessionService.isAdmin()) {
            if (getAccountDto.getAccountId() != null) {
                return accountRepository.findById(getAccountDto.getAccountId()).orElse(null);
            } else if (getAccountDto.getUserId() != null) {
                return accountRepository.findByUserId(getAccountDto.getUserId()).orElse(null);
            } else {
                throw new UnsupportedOperationException("Не заданы критерии для поиска аккаунта.");
            }
        } else {
            return accountRepository.findByUserId(userSessionService.getUser().getId()).orElse(null);
        }
    }

    @Transactional
    public Account create() {
        Long id = userSessionService.getUser().getId();
        return createAccount(id);
    }

    @Transactional
    public Account create(Long userId) {
        log.info("создание аккаунта для пользователя {}", userId);
        return createAccount(userId);
    }

    private Account createAccount(Long userId) {
        Optional<Account> accountOptional = accountRepository.findByUserId(userId);
        if (accountOptional.isPresent()) {
            log.info("аккаунт уже создан");
            return accountOptional.get();
        }
        Account account = new Account();
        account.setUserId(userId);
        return accountRepository.save(account);
    }
}
