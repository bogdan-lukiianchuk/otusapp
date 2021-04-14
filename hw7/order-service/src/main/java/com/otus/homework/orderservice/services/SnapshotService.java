package com.otus.homework.orderservice.services;

import com.otus.homework.orderservice.dto.rest.OrderDto;
import com.otus.homework.orderservice.entities.Order;
import com.otus.homework.orderservice.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class SnapshotService {
    private final OrderRepository orderRepository;

    @Transactional
    public void addSnapshot(OrderDto orderDto) {
        Optional<Order> orderOpt = orderRepository.findById(orderDto.getId());
        if (orderOpt.isPresent()) {
            Order order = orderOpt.get();
            if (order.getLastUpdate().equals(orderDto.getLastUpdateTime())) {
                return;
            }
            order.setData(orderDto);
            order.setLastUpdate(orderDto.getLastUpdateTime());
            orderRepository.save(order);
        } else {
            orderRepository.save(new Order(orderDto.getId(), orderDto.getUserId(), orderDto.getLastUpdateTime(), orderDto));
        }
    }

    public Order getSnapshot(Long orderId) {
        return orderRepository.findById(orderId).orElse(null);
    }
}
