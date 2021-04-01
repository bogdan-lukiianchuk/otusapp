package com.otus.homework.userserviceauth.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class JsonUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonUtils.class);

    private static ObjectMapper objectMapper = new ObjectMapper();

    public JsonUtils(ObjectMapper objectMapper) {
        JsonUtils.objectMapper = objectMapper;
    }

    public static String dump(Object object) {
        String res;
        try {
            res = objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            LOGGER.error("Ошибка сериализации объекта ", e);
            res = "";
        }
        return res;
    }

}
