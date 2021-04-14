package com.otus.homework.userserviceauth.services;

import com.otus.homework.userserviceauth.config.ApplicationProperties;
import com.otus.homework.userserviceauth.dto.AccountDto;
import com.otus.homework.userserviceauth.dto.request.CreateBillingAccountDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RequiredArgsConstructor
@Service
public class BillingIntegrationService {
    private final ApplicationProperties applicationProperties;
    private final RestTemplate restTemplate;

    public AccountDto createAccount(Long userId) {
        var body = new CreateBillingAccountDto(userId);
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add(HttpHeaders.AUTHORIZATION, applicationProperties.getAccessToken());
        URI uri = UriComponentsBuilder.fromUriString(
                getPath(applicationProperties.getBaseUrl(), applicationProperties.getMethod().getCreate()))
                .build().toUri();
        return restTemplate.exchange(uri, HttpMethod.POST, new HttpEntity<>(body, headers), AccountDto.class).getBody();
    }

    private String getPath(String url, String method) {
        return url + method;
    }
}
