package com.otus.homework.billingservice.services;

import com.otus.homework.billingservice.config.ApplicationProperties;
import com.otus.homework.billingservice.dto.CancelDto;
import com.otus.homework.billingservice.dto.OperationDto;
import com.otus.homework.billingservice.entities.Account;
import com.otus.homework.billingservice.entities.OperationCode;
import com.otus.homework.billingservice.entities.OperationLog;
import com.otus.homework.billingservice.enums.OperationStatus;
import com.otus.homework.billingservice.exceptions.ApplicationException;
import com.otus.homework.billingservice.model.OperationResponse;
import com.otus.homework.billingservice.repositories.AccountRepository;
import com.otus.homework.billingservice.repositories.OperationCodeRepository;
import com.otus.homework.billingservice.repositories.OperationLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class OperationService {
    private final OperationLogRepository operationLogRepository;
    private final OperationCodeRepository operationCodeRepository;
    private final AccountRepository accountRepository;
    private final ApplicationProperties properties;
    private final JsonUtils jsonUtils;
    private final UserSessionService userSessionService;

    @Transactional
    public OperationResponse cancel(CancelDto cancelDto) {
        Optional<OperationLog> operationLogOpt = operationLogRepository.findById(cancelDto.getOperationUuid());
        if (operationLogOpt.isPresent()) {
            return new OperationResponse("Операция уже проведена", OperationStatus.SKIPPED);
        }
        Account account = accountRepository.findById(cancelDto.getAccountId())
                .orElseThrow(() -> new ApplicationException(String.format(
                        "Невозможно выполнить операцию. Не найден аккаунт %s.", cancelDto.getAccountId())));
        OperationLog cancelOperation = operationLogRepository.findById(cancelDto.getCancelOperationUuid()).orElseThrow();
        OperationDto operationDto = jsonUtils.readValue(cancelOperation.getData(), OperationDto.class);
        if (operationDto == null) {
            throw new ApplicationException("Невозможно выполнить операцию.");
        }
        account.setMoney(account.getMoney() +- operationDto.getMoney());
        operationLogRepository.save(new OperationLog(
                cancelDto.getOperationUuid(),
                account.getAccountId(),
                Instant.now(),
                jsonUtils.writeValue(cancelDto)));
        accountRepository.save(account);
        return new OperationResponse("Отмена проведена успешно", OperationStatus.SUCCESS);
    }

    @Transactional
    public OperationResponse execute(OperationDto operationDto) {
        Account account = accountRepository.findByUserId(userSessionService.getUser().getId())
                .orElseThrow(() -> new ApplicationException(String.format(
                        "Невозможно выполнить операцию. Не найден аккаунт для пользователя %s.", userSessionService.getUser().getId())));
        Optional<OperationLog> operationLog = operationLogRepository.findById(operationDto.getOperationUuid());
        if (operationLog.isPresent()) {
            return new OperationResponse("Операция уже проведена", OperationStatus.SKIPPED, account.getMoney());
        }
        OperationCode operationCode = operationCodeRepository.findByOperation(operationDto.getOperationCode())
                .orElseThrow(() -> new ApplicationException(String.format(
                        "Неизвестная операция \"%s\".", operationDto.getOperationCode())));
        OperationResponse result = executeOperation(operationDto, account, operationCode);
        if (result.getStatus() != OperationStatus.SUCCESS) {
            return result;
        }
        account.setMoney(result.getMoney());
        operationLogRepository.save(new OperationLog(
                operationDto.getOperationUuid(),
                account.getAccountId(),
                Instant.now(),
                jsonUtils.writeValue(operationDto)));
        return result;
    }

    private OperationResponse executeOperation(OperationDto operationDto, Account account, OperationCode operationCode) {
        OperationResponse result;
        switch (operationCode.getId()) {
            case 1:
                result = topUp(operationDto, account);
                break;
            case 2:
                result = withdraw(operationDto, account);
                break;
            default:
                throw new UnsupportedOperationException("Неизвестный тип параметра");
        }
        return result;
    }

    private OperationResponse topUp(OperationDto operationDto, Account account) {
        if (operationDto.getMoney() <= 0) {
            throw new ApplicationException("Сумма для пополнения не может быть 0 или меньше");
        }
        long result = account.getMoney() + operationDto.getMoney();
        if (result > properties.getMaxAccountMoney()) {
            return new OperationResponse(
                    String.format("Невозможно пополнить счет. Максимальная сумма счета не должна превышать %s",
                            properties.getMaxAccountMoney()), OperationStatus.FAIL);
        }
        if (result < account.getMoney()) {
            return new OperationResponse(
                    String.format("Невозможно пополнить счет на сумму %s. Результат некорректен",
                            operationDto.getMoney()), OperationStatus.FAIL);
        }
        return new OperationResponse("Операция пополнения счета выполнена успешно", OperationStatus.SUCCESS, result);
    }

    private OperationResponse withdraw(OperationDto operationDto, Account account) {
        if (operationDto.getMoney() >= 0) {
            throw new ApplicationException("Сумма снятия не может быть положительной");
        }
        long result = account.getMoney() + operationDto.getMoney();
        if (result < properties.getMinAccountMoney() || result > account.getMoney()) {
            return new OperationResponse("Невозможно выполнить операцию. Недостаточно денег на счете.", OperationStatus.FAIL);
        }
        return new OperationResponse("Оплата выполнена успешно", OperationStatus.SUCCESS, result);
    }
}
