package com.otus.homework.orderservice.services;

import com.otus.homework.orderservice.dto.kafka.EmailNotificationTaskDto;
import com.otus.homework.orderservice.dto.rest.OrderDto;
import com.otus.homework.orderservice.dto.rest.template.AccountDto;
import com.otus.homework.orderservice.dto.rest.template.OperationDto;
import com.otus.homework.orderservice.dto.rest.template.OperationResponseDto;
import com.otus.homework.orderservice.entities.OrderEvent;
import com.otus.homework.orderservice.enums.EventType;
import com.otus.homework.orderservice.enums.OperationStatus;
import com.otus.homework.orderservice.enums.OrderStatus;
import com.otus.homework.orderservice.enums.Type;
import com.otus.homework.orderservice.exceptions.ApplicationException;
import com.otus.homework.orderservice.model.UpdateOrderJson;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class PayService {
    private final BillingIntegrationService billingIntegrationService;
    private final EmailNotificationKafkaService emailNotificationKafkaService;
    private final UserSessionService userSessionService;

    public OrderEvent payOrder(Long orderId, HttpServletRequest request, OrderDto order) {
        Long accountId = Optional.ofNullable(billingIntegrationService.getAccount(request))
                .map(AccountDto::getAccountId)
                .orElseThrow(() -> new ApplicationException("Ошибка, аккаунт не найден."));
        OperationDto operationDto = new OperationDto("WITHDRAW", UUID.randomUUID(), accountId, -order.getTotalCost());
        OperationResponseDto response = billingIntegrationService.executePay(operationDto, request);
        EmailNotificationTaskDto taskDto;
        OrderEvent orderEvent;
        if (response.getStatus() == OperationStatus.SKIPPED) {
            return null;
        }
        if (response.getStatus() == OperationStatus.SUCCESS) {
            taskDto = createEmailNotificationTaskDto(orderId, Type.ORDER_CREATED, null);
            orderEvent = createEvent(orderId, OrderStatus.DONE);
        } else {
            taskDto = createEmailNotificationTaskDto(orderId, Type.INSUFFICIENT_FUNDS, response.getMessage());
            orderEvent = createEvent(orderId, OrderStatus.PAY_ERROR);
        }
        emailNotificationKafkaService.sendToKafka(taskDto);
        return orderEvent;
    }

    private EmailNotificationTaskDto createEmailNotificationTaskDto(Long orderId, Type insufficientFunds, String message) {
        return new EmailNotificationTaskDto(
                userSessionService.getUserId(),
                userSessionService.getUser().getName(),
                insufficientFunds, orderId, message);
    }

    private OrderEvent createEvent(Long orderId, OrderStatus payError) {
        return new OrderEvent(
                userSessionService.getUserId(),
                orderId,
                Instant.now(),
                EventType.CHANGE_STATUS,
                new UpdateOrderJson(payError));
    }
}
