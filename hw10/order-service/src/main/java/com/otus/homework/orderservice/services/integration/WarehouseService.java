package com.otus.homework.orderservice.services.integration;

import com.otus.homework.orderservice.config.ApplicationAccessProperties;
import com.otus.homework.orderservice.config.ApplicationWarehouseProperties;
import com.otus.homework.orderservice.dto.rest.ProductItemDto;
import com.otus.homework.orderservice.dto.rest.warehouse.ReservationDto;
import com.otus.homework.orderservice.dto.rest.warehouse.SupplyDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class WarehouseService {
    private final ApplicationWarehouseProperties properties;
    private final ApplicationAccessProperties accessProperties;
    private final RestTemplate restTemplate;

    public boolean reserve(Long orderId, List<ProductItemDto> products) {
        try {
            MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
            headers.add(HttpHeaders.AUTHORIZATION, accessProperties.getTokenOrderSrv());
            String url = properties.getServiceUrl() + properties.getMethod().getReserve();
            List<SupplyDto> supplies = products
                    .stream()
                    .map(it -> new SupplyDto(it.getProductId(), it.getCount()))
                    .collect(Collectors.toList());
            ReservationDto reservationDto = new ReservationDto(orderId, supplies);
            HttpEntity<ReservationDto> httpEntity = new HttpEntity<>(reservationDto, headers);
            restTemplate.postForEntity(url, httpEntity, Void.class);
            return true;
        } catch (Exception e) {
            log.error("Ошибка бронирования, {}", e.getMessage());
            return false;
        }
    }

    public void cancel(Long orderId) {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add(HttpHeaders.AUTHORIZATION, accessProperties.getTokenOrderSrv());
        String url = properties.getServiceUrl() + properties.getMethod().getCancel();
        Map<String, Long> order = Map.of("orderId", orderId);
        restTemplate.postForEntity(url, new HttpEntity<>(order, headers), Void.class);
    }

    public void transfer(Long orderId) {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add(HttpHeaders.AUTHORIZATION, accessProperties.getTokenOrderSrv());
        String url = properties.getServiceUrl() + properties.getMethod().getTransfer();
        Map<String, Long> order = Map.of("orderId", orderId);
        restTemplate.postForEntity(url, new HttpEntity<>(order, headers), Void.class);
    }
}
