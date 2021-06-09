package com.otus.homework.orderservice.services.integration;

import com.otus.homework.orderservice.config.ApplicationAccessProperties;
import com.otus.homework.orderservice.config.ApplicationProductProperties;
import com.otus.homework.orderservice.dto.rest.ProductDto;
import com.otus.homework.orderservice.dto.rest.warehouse.ReservationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.*;

@RequiredArgsConstructor
@Service
public class ProductService {
    private final Map<String, ProductDto> products = new HashMap<>();
    private final RestTemplate restTemplate;
    private final ApplicationAccessProperties accessProperties;
    private final ApplicationProductProperties properties;

    @PostConstruct
    public void fillCache() {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add(HttpHeaders.AUTHORIZATION, accessProperties.getTokenOrderSrv());
        HttpEntity<ReservationDto> httpEntity = new HttpEntity<>(headers);
        ProductDto[] response = restTemplate
                .exchange(properties.getServiceUrl() + properties.getMethod().getList(),
                        HttpMethod.GET, httpEntity, ProductDto[].class)
                .getBody();
        Arrays.stream(Objects.requireNonNull(response))
                .forEach(productDto -> products.put(productDto.getName(), productDto));
    }

    public Optional<ProductDto> findByName(String name) {
        return Optional.of(products.get(name));
    }
}
