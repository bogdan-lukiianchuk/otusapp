package com.otus.homework.orderservice.controllers;

import com.otus.homework.orderservice.dto.rest.OrderCreatedDto;
import com.otus.homework.orderservice.dto.rest.SetItemDto;
import com.otus.homework.orderservice.dto.rest.UpdateOrderDto;
import com.otus.homework.orderservice.services.EventOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RestController
@Validated
@RequestMapping(value = "/api/orders", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderController {
    private final EventOrderService eventOrderService;

    @PostMapping("/")
    public OrderCreatedDto createOrder() {
        return new OrderCreatedDto(eventOrderService.createOrder());
    }

    @PostMapping("/{orderId}")
    public void updateOrder(@PathVariable("orderId") Long orderId, @RequestBody UpdateOrderDto updateOrderDto, HttpServletRequest request) {
        eventOrderService.updateOrder(orderId, updateOrderDto, request);
    }

    @PostMapping("/{orderId}/products")
    public void setOrderProducts(@PathVariable("orderId") Long orderId, @RequestBody SetItemDto setItemDto) {
        eventOrderService.setOrderProducts(orderId, setItemDto);
    }
}
