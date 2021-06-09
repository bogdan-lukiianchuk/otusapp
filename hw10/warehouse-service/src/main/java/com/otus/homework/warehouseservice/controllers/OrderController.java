package com.otus.homework.warehouseservice.controllers;

import com.otus.homework.warehouseservice.dto.OrderDto;
import com.otus.homework.warehouseservice.dto.ReservationDto;
import com.otus.homework.warehouseservice.services.OrderService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"Order"})
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/warehouse/orders")
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/cancel")
    public void cancel(@RequestBody OrderDto orderDto) {
        orderService.cancel(orderDto);
    }

    @PostMapping("/transfer")
    public void toTransfer(@RequestBody OrderDto orderDto) {
        orderService.toTransfer(orderDto);
    }

    @PostMapping("/reserve")
    public void reserve(@RequestBody ReservationDto reservationDto) {
        orderService.reserve(reservationDto);
    }
}
