package com.otus.homework.orderservice.services;

import com.otus.homework.orderservice.dto.rest.OrderDto;
import com.otus.homework.orderservice.dto.rest.billing.CancelDto;
import com.otus.homework.orderservice.dto.rest.billing.OperationResponseDto;
import com.otus.homework.orderservice.entities.OrderPayIdempotenceOperation;
import com.otus.homework.orderservice.entities.OrderStage;
import com.otus.homework.orderservice.enums.OperationStatus;
import com.otus.homework.orderservice.enums.OrderStages;
import com.otus.homework.orderservice.repositories.OrderPayIdempotenceOperationRepository;
import com.otus.homework.orderservice.repositories.OrderStageRepository;
import com.otus.homework.orderservice.services.integration.BillingIntegrationService;
import com.otus.homework.orderservice.services.integration.WarehouseService;
import com.otus.homework.orderservice.utils.TransactionHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class SagaService {
    private final BillingIntegrationService billingIntegrationService;
    private final WarehouseService warehouseService;
    private final OrderStageRepository orderStageRepository;
    private final OrderAggregateService orderAggregateService;
    private final TransactionHelper transactionHelper;
    private final OrderPayIdempotenceOperationRepository payIdempotenceOperationRepository;

    @Scheduled(fixedDelayString = "PT2S")
    public void doAfterPayError() {
        orderStageRepository.getAllByStage(OrderStages.PAY_ERROR.name())
                .forEach(orderStage -> {
                    log.debug("Отмена после неудачной отправки");
                    transactionHelper.doInTransaction(() -> {
                        OrderStage forUpdate = orderStageRepository
                                .getForUpdate(orderStage.getOrderId())
                                .orElseThrow();
                        if (forUpdate.getStage() != OrderStages.PAY_ERROR) {
                            return;
                        }
                        OrderDto order = orderAggregateService.getOrder(forUpdate.getOrderId());
                        warehouseService.cancel(order.getId());
                        forUpdate.setStage(OrderStages.RESERVE_CANCEL);
                        log.debug("Отменено резервирование заказа {}", order);
                    });
                });
    }

    @Scheduled(fixedDelayString = "PT2S")
    public void cancelReservation() {
        orderStageRepository.getAllByStage(OrderStages.PAY_CANCEL.name())
                .forEach(orderStage -> {
                    log.debug("Отмена резервирования после отмененной оплаты");
                    transactionHelper.doInTransaction(() -> {
                        OrderStage forUpdate = orderStageRepository
                                .getForUpdate(orderStage.getOrderId())
                                .orElseThrow();
                        if (forUpdate.getStage() != OrderStages.PAY_CANCEL) {
                            return;
                        }
                        OrderDto order = orderAggregateService.getOrder(forUpdate.getOrderId());
                        warehouseService.cancel(order.getId());
                        forUpdate.setStage(OrderStages.RESERVE_CANCEL);
                        log.debug("Отменено резервирование заказа {}", order);
                    });
                });
    }

    @Scheduled(fixedDelayString = "PT2S")
    public void doAfterDeliveryError() {
        orderStageRepository.getAllByStage(OrderStages.DELIVERY_ERROR.name())
                .forEach(orderStage -> {
                    log.debug("Отмена доставки после ошибки доставки");
                    transactionHelper.doInTransaction(() -> {
                        OrderStage forUpdate = orderStageRepository
                                .getForUpdate(orderStage.getOrderId())
                                .orElseThrow();
                        if (forUpdate.getStage() != OrderStages.DELIVERY_ERROR) {
                            return;
                        }
                        OrderPayIdempotenceOperation idempotenceOperation = payIdempotenceOperationRepository
                                .findById(orderStage.getOrderId())
                                .orElseThrow();
                        OperationResponseDto cancel = billingIntegrationService
                                .cancel(new CancelDto(UUID.randomUUID(), idempotenceOperation.getKey(), idempotenceOperation.getAccountId()));
                        if (cancel.getStatus() == OperationStatus.SUCCESS) {
                            forUpdate.setStage(OrderStages.PAY_CANCEL);
                            log.debug("Отменена доставка. Ступень заказа {}", forUpdate);
                        }
                    });
                });
    }
}
