package com.otus.homework.orderservice.services;

import com.otus.homework.orderservice.entities.OrderStage;
import com.otus.homework.orderservice.repositories.OrderStageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderStageService {
    private final OrderStageRepository orderStageRepository;

    public List<OrderStage> getStages(Long orderId) {
        return orderStageRepository.findAllByOrderIdOrderByCreateTime(orderId);
    }
}
