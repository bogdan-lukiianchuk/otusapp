package com.otus.homework.orderservice.services;

import com.otus.homework.orderservice.dto.UpdateOrderDto;
import com.otus.homework.orderservice.dto.rest.OrderDto;
import com.otus.homework.orderservice.dto.rest.SetItemDto;
import com.otus.homework.orderservice.dto.rest.SortAndFilter;
import com.otus.homework.orderservice.dto.rest.UpdateOrderStatusDto;
import com.otus.homework.orderservice.entities.OrderEvent;
import com.otus.homework.orderservice.entities.OrderIdempotenceOperation;
import com.otus.homework.orderservice.enums.OrderStatus;
import com.otus.homework.orderservice.exceptions.ValidationException;
import com.otus.homework.orderservice.repositories.OrderEventRepository;
import com.otus.homework.orderservice.repositories.OrderIdempotenceRepository;
import com.otus.homework.orderservice.repositories.OrderSequenceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
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
    private final OrderIdempotenceRepository orderIdempotenceRepository;
    private final OrderWorkService orderWorkService;

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

    public void updateOrderStatus(Long orderId, UpdateOrderStatusDto updateOrderStatusDto, HttpServletRequest request) {
        OrderDto order = orderAggregateService.getOrder(orderId);
        if (order.getStatus().ordinal() > updateOrderStatusDto.getStatus().ordinal()) {
            throw new ValidationException("Нельзя изменить статус заказа на " + updateOrderStatusDto.getStatus());
        }
        if (updateOrderStatusDto.getStatus() == OrderStatus.WAITING_PAY && order.getDeliveryTime() == null) {
            throw new UnsupportedOperationException("Не указано время доставки");
        }
        addEvent(OrderEvent.createChangeStatusEvent(orderId, userSessionService.getUserId(), updateOrderStatusDto.getStatus()));
        if (updateOrderStatusDto.getStatus() != OrderStatus.WAITING_PAY) {
            return;
        }
        try {
            orderWorkService.createOrder(order, request);
        } catch (Exception e) {
            addEvent(OrderEvent.createChangeStatusEvent(orderId, userSessionService.getUserId(), OrderStatus.ERROR));
        }
    }

    @Transactional
    public void setOrderProducts(Long orderId, SetItemDto setItemDto) {
        OrderDto byId = getById(orderId);
        if (byId.getStatus() != OrderStatus.CREATED) {
            throw new ValidationException("Заказ недоступен для редактирования");
        }
        addEvent(OrderEvent.createAddProductEvent(orderId, userSessionService.getUserId(), setItemDto));
    }

    @Transactional
    public void updateOrder(Long orderId, UpdateOrderDto updateOrderDto) {
        OrderDto byId = getById(orderId);
        if (byId.getStatus() != OrderStatus.CREATED) {
            throw new ValidationException("Заказ недоступен для редактирования");
        }
        UpdateOrderDto updateOrderDto1 = new UpdateOrderDto(updateOrderDto.getDeliveryTime());
        addEvent(OrderEvent.createUpdateEvent(orderId, userSessionService.getUserId(), updateOrderDto1));
    }

    private Long createOrder() {
        Long orderId = orderSequenceRepository.nextVal();
        addEvent(OrderEvent.createEvent(orderId, userSessionService.getUserId()));
        return orderId;
    }

    private void addEvent(OrderEvent orderEvent) {
        orderEventRepository.save(orderEvent);
    }
}
