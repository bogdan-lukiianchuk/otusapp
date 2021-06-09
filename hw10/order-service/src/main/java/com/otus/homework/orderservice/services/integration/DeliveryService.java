package com.otus.homework.orderservice.services.integration;

import com.otus.homework.orderservice.config.ApplicationDeliveryProperties;
import com.otus.homework.orderservice.dto.rest.delivery.CancelReservationCourierDto;
import com.otus.homework.orderservice.dto.rest.delivery.CourierDto;
import com.otus.homework.orderservice.dto.rest.delivery.ReserveCourierDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Service
public class DeliveryService {
    private final RestTemplate restTemplate;
    private final ApplicationDeliveryProperties properties;

    public CourierDto getCourier(ReserveCourierDto reserveCourierDto) {
        return restTemplate.postForObject(
                properties.getServiceUrl() + properties.getMethod().getCourier(), reserveCourierDto, CourierDto.class);
    }

    public String cancel(CancelReservationCourierDto cancelReservationCourierDto) {
        return restTemplate.postForObject(
                properties.getServiceUrl() + properties.getMethod().getCancel(), cancelReservationCourierDto, String.class);
    }
}
