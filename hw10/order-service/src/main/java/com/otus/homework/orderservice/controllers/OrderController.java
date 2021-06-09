package com.otus.homework.orderservice.controllers;

import com.otus.homework.orderservice.dto.UpdateOrderDto;
import com.otus.homework.orderservice.dto.rest.OrderCreatedDto;
import com.otus.homework.orderservice.dto.rest.OrderStageDto;
import com.otus.homework.orderservice.dto.rest.SetItemDto;
import com.otus.homework.orderservice.dto.rest.UpdateOrderStatusDto;
import com.otus.homework.orderservice.services.EventOrderService;
import com.otus.homework.orderservice.services.OrderStageService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Api(tags = {"Orders"})
@RequiredArgsConstructor
@RestController
@Validated
@RequestMapping(value = "/api/orders", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderController {
    private final EventOrderService eventOrderService;
    private final OrderStageService orderStageService;

    @PostMapping("/")
    public OrderCreatedDto createOrder(@RequestHeader UUID requestId) {
        return new OrderCreatedDto(eventOrderService.createOrder(requestId));
    }

    @PostMapping("/status/{orderId}")
    public void updateOrderStatus(@PathVariable("orderId") Long orderId,
                                  @RequestBody UpdateOrderStatusDto updateOrderStatusDto,
                                  HttpServletRequest request) {
        eventOrderService.updateOrderStatus(orderId, updateOrderStatusDto, request);
    }

    @PostMapping("/{orderId}")
    public void updateOrder(@PathVariable("orderId") Long orderId, @RequestBody UpdateOrderDto updateOrderDto) {
        eventOrderService.updateOrder(orderId, updateOrderDto);
    }

    @PostMapping("/{orderId}/products")
    public void setOrderProducts(@PathVariable("orderId") Long orderId, @RequestBody SetItemDto setItemDto) {
        eventOrderService.setOrderProducts(orderId, setItemDto);
    }

    @GetMapping("/{orderId}/stages")
    public List<OrderStageDto> getOrderStages(@PathVariable("orderId") Long orderId){
        return orderStageService.getStages(orderId)
                .stream()
                .map(it -> new OrderStageDto(it.getOrderId(), it.getStage(), it.getCreateTime()))
                .collect(Collectors.toList());
    }
}
