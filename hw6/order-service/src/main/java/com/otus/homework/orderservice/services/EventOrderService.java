package com.otus.homework.orderservice.services;

import com.otus.homework.orderservice.dto.OrderDto;
import com.otus.homework.orderservice.dto.SetItemDto;
import com.otus.homework.orderservice.dto.SortAndFilter;
import com.otus.homework.orderservice.dto.UpdateOrderDto;
import com.otus.homework.orderservice.entities.OrderEvent;
import com.otus.homework.orderservice.enums.EventType;
import com.otus.homework.orderservice.enums.OrderStatus;
import com.otus.homework.orderservice.exceptions.ValidationException;
import com.otus.homework.orderservice.model.EmptyJson;
import com.otus.homework.orderservice.model.SetItemJson;
import com.otus.homework.orderservice.model.UpdateOrderJson;
import com.otus.homework.orderservice.repositories.OrderEventRepository;
import com.otus.homework.orderservice.repositories.OrderSequenceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@RequiredArgsConstructor
@Service
public class EventOrderService {
    private final OrderAggregateService orderAggregateService;
    private final UserSessionService userSessionService;
    private final OrderSequenceRepository orderSequenceRepository;
    private final OrderEventRepository orderEventRepository;

    public OrderDto getById(long id) {
        return orderAggregateService.getOrder(id);
    }

    public List<OrderDto> getOrdersForUser(SortAndFilter sortAndFilter) {
        return orderAggregateService.getForUser(userSessionService.getUserId(), sortAndFilter);
    }

    @Transactional
    public Long createOrder() {
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
    public void updateOrder(Long orderId, UpdateOrderDto updateOrderDto) {
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
