package com.otus.homework.orderservice.services;

import com.otus.homework.orderservice.dto.rest.OrderDto;
import com.otus.homework.orderservice.dto.rest.SetItemDto;
import com.otus.homework.orderservice.dto.rest.SortAndFilter;
import com.otus.homework.orderservice.dto.rest.UpdateOrderDto;
import com.otus.homework.orderservice.entities.OrderEvent;
import com.otus.homework.orderservice.entities.OrderIdempotenceOperation;
import com.otus.homework.orderservice.enums.EventType;
import com.otus.homework.orderservice.enums.OrderStatus;
import com.otus.homework.orderservice.exceptions.ValidationException;
import com.otus.homework.orderservice.model.EmptyJson;
import com.otus.homework.orderservice.model.SetItemJson;
import com.otus.homework.orderservice.model.UpdateOrderJson;
import com.otus.homework.orderservice.repositories.OrderEventRepository;
import com.otus.homework.orderservice.repositories.OrderIdempotenceRepository;
import com.otus.homework.orderservice.repositories.OrderSequenceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class EventOrderService {
    private final OrderAggregateService orderAggregateService;
    private final UserSessionService userSessionService;
    private final OrderSequenceRepository orderSequenceRepository;
    private final OrderEventRepository orderEventRepository;
    private final PayService payService;
    private final OrderIdempotenceRepository orderIdempotenceRepository;

    public OrderDto getById(long id) {
        return orderAggregateService.getOrder(id);
    }

    public List<OrderDto> getOrdersForUser(SortAndFilter sortAndFilter) {
        return orderAggregateService.getForUser(userSessionService.getUserId(), sortAndFilter);
    }

    @Transactional
    public Long createOrder(UUID requestId) {
        Optional<OrderIdempotenceOperation> idempotenceOperation = orderIdempotenceRepository.findById(requestId);
        if (idempotenceOperation.isPresent()) {
            return idempotenceOperation.get().getOrderId();
        }
        Long orderId = createOrder();
        orderIdempotenceRepository.save(new OrderIdempotenceOperation(requestId, orderId));
        return orderId;
    }

    private Long createOrder() {
        Long orderId = orderSequenceRepository.nextVal();
        OrderEvent orderEvent = new OrderEvent(
                userSessionService.getUserId(),
                orderId,
                Instant.now(),
                EventType.CREATE,
                new EmptyJson());
        addEvent(orderEvent);
        return orderId;
    }

    @Transactional
    public void updateOrder(Long orderId, UpdateOrderDto updateOrderDto, HttpServletRequest request) {
        OrderDto order = orderAggregateService.getOrder(orderId);
        if (order.getStatus().ordinal() > updateOrderDto.getStatus().ordinal()) {
            throw new ValidationException("Нельзя изменить статус заказа на " + updateOrderDto.getStatus());
        }
        OrderEvent orderEvent = new OrderEvent(
                userSessionService.getUserId(),
                orderId,
                Instant.now(),
                EventType.CHANGE_STATUS,
                new UpdateOrderJson(updateOrderDto.getStatus()));
        addEvent(orderEvent);
    }

    @Transactional
    public void setOrderProducts(Long orderId, SetItemDto setItemDto) {
        OrderDto byId = getById(orderId);
        if (byId.getStatus() != OrderStatus.CREATED) {
            throw new ValidationException("Заказ недоступен для редактирования");
        }
        OrderEvent orderEvent = new OrderEvent(
                userSessionService.getUserId(),
                orderId,
                Instant.now(),
                EventType.ADD_PRODUCT,
                new SetItemJson(setItemDto.getName(), setItemDto.getCount()));
        addEvent(orderEvent);
    }

    private void addEvent(OrderEvent orderEvent) {
        orderEventRepository.save(orderEvent);
    }
}
