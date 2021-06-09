package com.otus.homework.orderservice.services;

import com.otus.homework.orderservice.dto.UpdateOrderDto;
import com.otus.homework.orderservice.dto.kafka.EmailNotificationTaskDto;
import com.otus.homework.orderservice.dto.rest.OrderDto;
import com.otus.homework.orderservice.dto.rest.billing.AccountDto;
import com.otus.homework.orderservice.dto.rest.billing.OperationDto;
import com.otus.homework.orderservice.dto.rest.billing.OperationResponseDto;
import com.otus.homework.orderservice.dto.rest.delivery.CourierDto;
import com.otus.homework.orderservice.dto.rest.delivery.ReserveCourierDto;
import com.otus.homework.orderservice.entities.OrderEvent;
import com.otus.homework.orderservice.entities.OrderPayIdempotenceOperation;
import com.otus.homework.orderservice.entities.OrderStage;
import com.otus.homework.orderservice.enums.OrderStages;
import com.otus.homework.orderservice.enums.OrderStatus;
import com.otus.homework.orderservice.enums.Type;
import com.otus.homework.orderservice.exceptions.ApplicationException;
import com.otus.homework.orderservice.repositories.OrderEventRepository;
import com.otus.homework.orderservice.repositories.OrderPayIdempotenceOperationRepository;
import com.otus.homework.orderservice.repositories.OrderStageRepository;
import com.otus.homework.orderservice.services.integration.BillingIntegrationService;
import com.otus.homework.orderservice.services.integration.DeliveryService;
import com.otus.homework.orderservice.services.integration.WarehouseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrderWorkService {
    private final BillingIntegrationService billingIntegrationService;
    private final EmailNotificationKafkaService emailNotificationKafkaService;
    private final UserSessionService userSessionService;
    private final DeliveryService deliveryService;
    private final WarehouseService warehouseService;
    private final OrderStageRepository orderStageRepository;
    private final OrderEventRepository orderEventRepository;
    private final OrderPayIdempotenceOperationRepository payIdempotenceOperationRepository;

    /*
     * зарезервировать товары в сервисе склада
     * оплатить заказ
     * отправить на доставку
     * */
    public void createOrder(OrderDto order, HttpServletRequest request) {
        log.debug("Получен заказ {}", order);
        boolean reserved = warehouseService.reserve(order.getId(), order.getItems());
        if (!reserved) {
            log.error("Ошибка резервирования, отсутствует товар на складе для заказа %{}", order.getId());
            throw new ApplicationException("Ошибка резервирования, отсутствует товар на складе.");
        }
        OperationResponseDto operationResponseDto = executePay(order, request);
        switch (operationResponseDto.getStatus()) {
            case SKIPPED:
                log.debug("Статус оплаты заказа - 'пропущен'");
                return;
            case FAIL:
                log.debug("Статус оплаты заказа - 'ошибка'");
                doForPayFail(order, operationResponseDto);
                break;
            case SUCCESS:
                log.debug("Статус оплаты заказа - 'успешно'");
                doForPaySuccess(order);
                break;
            default:
                throw new UnsupportedOperationException("Неизвестный статус операции");
        }
    }

    private void doForPaySuccess(OrderDto order) {
        log.debug("Начинаем отправку заказа {}", order);
        try {
            CourierDto courier = deliveryService.getCourier(new ReserveCourierDto(order.getDeliveryTime(), order.getId()));
            if (courier.getName().isBlank()) {
                orderStageRepository.save(new OrderStage(order.getId(), OrderStages.DELIVERY_ERROR, LocalDateTime.now()));
                log.error("Курьер не назначен, нет свободного на указанное время {}", order.getDeliveryTime());
                throw new ApplicationException("Не удалось забронировать доставку на указанное время.");
            }
            orderEventRepository.save(
                    OrderEvent.createUpdateEvent(
                            order.getId(),
                            userSessionService.getUserId(),
                            new UpdateOrderDto(order.getDeliveryTime(), courier.getName()))
            );
        } catch (Exception e) {
            orderStageRepository.save(new OrderStage(order.getId(), OrderStages.DELIVERY_ERROR, LocalDateTime.now()));
            log.error("Курьер не назначен, произошла ошибка");
            throw new ApplicationException("Не удалось забронировать доставку на указанное время.", e);
        }
        try {
            log.debug("Отправка заказа {}", order.getId());
            warehouseService.transfer(order.getId());
        } catch (Exception e) {
            orderStageRepository.save(new OrderStage(order.getId(), OrderStages.DELIVERY_ERROR, LocalDateTime.now()));
            log.error("Ошибка отправки заказа методом 'трансфер'");
            throw new ApplicationException("Не удалось отправить заказ");
        }
        EmailNotificationTaskDto taskDto = createEmailNotificationTaskDto(order.getId(), Type.ORDER_CREATED, null);
        OrderEvent orderEvent = OrderEvent.createChangeStatusEvent(order.getId(), order.getUserId(), OrderStatus.DONE);
        orderEventRepository.save(orderEvent);
        emailNotificationKafkaService.sendToKafka(taskDto);
        log.debug("Заказ {} успешно отправлен", order.getId());
    }

    private void doForPayFail(OrderDto order, OperationResponseDto operationResponseDto) {
        log.debug("Отмена после неудачной оплаты заказа {}", order);
        orderStageRepository.save(new OrderStage(order.getId(), OrderStages.PAY_ERROR, LocalDateTime.now()));
        EmailNotificationTaskDto taskDto = createEmailNotificationTaskDto(
                order.getId(),
                Type.INSUFFICIENT_FUNDS,
                operationResponseDto.getMessage());
        OrderEvent orderEvent = OrderEvent.createChangeStatusEvent(order.getId(), order.getUserId(), OrderStatus.ERROR);
        orderEventRepository.save(orderEvent);
        emailNotificationKafkaService.sendToKafka(taskDto);
        log.debug("Заказу выставлен уровень PAY_ERROR");
        throw new ApplicationException(operationResponseDto.getMessage());
    }

    private OperationResponseDto executePay(OrderDto order, HttpServletRequest request) {
        log.debug("Оплата заказа {}", order);
        UUID payOperationId = UUID.randomUUID();
        Long accountId = Optional.ofNullable(billingIntegrationService.getAccount(request))
                .map(AccountDto::getAccountId)
                .orElseThrow(() -> new ApplicationException("Ошибка, аккаунт не найден."));
        OperationDto operationDto = new OperationDto("WITHDRAW", payOperationId, accountId, -order.getTotalCost());
        OperationResponseDto operationResponseDto = billingIntegrationService.executePay(operationDto, request);
        payIdempotenceOperationRepository.save(new OrderPayIdempotenceOperation(order.getId(), payOperationId, accountId));
        log.debug("Оплата заказа {} проведена, статус '{}' {}",
                order, operationResponseDto.getStatus(), operationResponseDto.getMessage());
        return operationResponseDto;
    }

    private EmailNotificationTaskDto createEmailNotificationTaskDto(Long orderId, Type insufficientFunds, String message) {
        return new EmailNotificationTaskDto(
                userSessionService.getUserId(),
                userSessionService.getUser().getName(),
                insufficientFunds, orderId, message);
    }
}
