package com.otus.homework.userservice.services;

import com.otus.homework.userservice.dto.UserDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class UserAuthIntegrationService {
    private final String servicePath;
    private final RestTemplate restTemplate;
    private final String accessToken;

    public UserAuthIntegrationService(@Value("${auth.service.path}") String servicePath,
                                      RestTemplate restTemplate,
                                      @Value("${auth.service.access-token}") String accessToken) {
        this.servicePath = servicePath;
        this.restTemplate = restTemplate;
        this.accessToken = accessToken;
    }

    public void update(UserDto userDto) {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Authorization", accessToken);
        HttpEntity<UserDto> httpEntity = new HttpEntity<>(userDto, headers);
        restTemplate.exchange(servicePath, HttpMethod.POST, httpEntity, UserDto.class);
    }
}
