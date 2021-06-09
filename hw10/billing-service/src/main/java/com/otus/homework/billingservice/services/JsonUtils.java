package com.otus.homework.billingservice.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class JsonUtils {
    private final ObjectMapper objectMapper;

    public String writeValue(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error("ошибка записи объекта {}", object, e);
            return "";
        }
    }

    public <T> T readValue(String data, Class<T> className) {
        try {
            return objectMapper.readValue(data, className);
        } catch (JsonProcessingException e) {
            log.error("ошибка чтения объекта {}", data, e);
            return null;
        }
    }
}
