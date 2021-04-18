package com.otus.homework.orderservice.controllers;

import com.otus.homework.orderservice.dto.rest.OrderDto;
import com.otus.homework.orderservice.dto.rest.SortAndFilter;
import com.otus.homework.orderservice.services.EventOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/orders")
@RestController
public class GetOrdersControllers {
    private final EventOrderService eventOrderService;

    @GetMapping("/{id:\\d+}")
    public OrderDto getById(@PathVariable("id") long id) {
        return eventOrderService.getById(id);
    }

    @GetMapping("/")
    public List<OrderDto> getOrders(SortAndFilter sortAndFilter) {
        return eventOrderService.getOrdersForUser(sortAndFilter);
    }
}
