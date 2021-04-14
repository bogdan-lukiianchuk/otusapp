package com.otus.homework.orderservice.services;

import com.otus.homework.orderservice.config.ApplicationProperties;
import com.otus.homework.orderservice.dto.rest.template.AccountDto;
import com.otus.homework.orderservice.dto.rest.template.OperationDto;
import com.otus.homework.orderservice.dto.rest.template.OperationResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@Service
public class BillingIntegrationService {
    private final ApplicationProperties applicationProperties;
    private final RestTemplate restTemplate;

    public OperationResponseDto executePay(OperationDto operationDto, HttpServletRequest request) {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add(HttpHeaders.AUTHORIZATION, request.getHeader(HttpHeaders.AUTHORIZATION));
        HttpEntity<OperationDto> httpEntity = new HttpEntity<>(operationDto, headers);
        return restTemplate.postForEntity(
                getPath(applicationProperties.getBaseUrl(), applicationProperties.getMethod().getOperations()),
                httpEntity, OperationResponseDto.class).getBody();
    }

    public AccountDto getAccount(HttpServletRequest request) {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add(HttpHeaders.AUTHORIZATION, request.getHeader(HttpHeaders.AUTHORIZATION));
        return restTemplate.postForEntity(
                getPath(applicationProperties.getBaseUrl(), applicationProperties.getMethod().getFind()),
                new HttpEntity<>(headers), AccountDto.class).getBody();
    }

    private String getPath(String url, String method) {
        return url + method;
    }
}
