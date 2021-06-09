package com.otus.homework.orderservice.services.integration;

import com.otus.homework.orderservice.config.ApplicationAccessProperties;
import com.otus.homework.orderservice.config.ApplicationBillingProperties;
import com.otus.homework.orderservice.dto.rest.billing.AccountDto;
import com.otus.homework.orderservice.dto.rest.billing.CancelDto;
import com.otus.homework.orderservice.dto.rest.billing.OperationDto;
import com.otus.homework.orderservice.dto.rest.billing.OperationResponseDto;
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
    private final ApplicationBillingProperties properties;
    private final ApplicationAccessProperties accessProperties;
    private final RestTemplate restTemplate;

    public OperationResponseDto executePay(OperationDto operationDto, HttpServletRequest request) {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add(HttpHeaders.AUTHORIZATION, request.getHeader(HttpHeaders.AUTHORIZATION));
        HttpEntity<OperationDto> httpEntity = new HttpEntity<>(operationDto, headers);
        return restTemplate.postForEntity(
                getPath(properties.getAccountServiceUrl(), properties.getMethod().getOperations()),
                httpEntity, OperationResponseDto.class).getBody();
    }

    public AccountDto getAccount(HttpServletRequest request) {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add(HttpHeaders.AUTHORIZATION, request.getHeader(HttpHeaders.AUTHORIZATION));
        return restTemplate.postForEntity(
                getPath(properties.getAccountServiceUrl(), properties.getMethod().getFind()),
                new HttpEntity<>(headers), AccountDto.class).getBody();
    }

    public OperationResponseDto cancel(CancelDto cancelDto) {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add(HttpHeaders.AUTHORIZATION, accessProperties.getTokenOrderSrv());
        HttpEntity<CancelDto> httpEntity = new HttpEntity<>(cancelDto, headers);
        return restTemplate.postForEntity(
                getPath(properties.getAccountServiceUrl(), properties.getMethod().getCancel()),
                httpEntity, OperationResponseDto.class).getBody();
    }

    private String getPath(String url, String method) {
        return url + method;
    }
}
