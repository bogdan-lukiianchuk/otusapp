package com.otus.homework.deliveryservice.controllers;

import com.otus.homework.deliveryservice.dto.CancelReservationCourierDto;
import com.otus.homework.deliveryservice.dto.CourierDto;
import com.otus.homework.deliveryservice.dto.ReserveCourierDto;
import com.otus.homework.deliveryservice.services.DeliveryService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@Api(tags = {"Delivery"})
@RestController
@RequestMapping("/api/delivery")
public class DeliveryController {
    private final DeliveryService deliveryService;

    @PostMapping("/getCourier")
    public CourierDto getCourier(@RequestBody ReserveCourierDto reserveCourierDto) {
        return new CourierDto(deliveryService.getFreeCourier(reserveCourierDto));
    }

    @PostMapping("/cancel")
    public void cancel(@RequestBody CancelReservationCourierDto cancelReservationCourierDto) {
        deliveryService.cancelDelivery(cancelReservationCourierDto);
    }
}
