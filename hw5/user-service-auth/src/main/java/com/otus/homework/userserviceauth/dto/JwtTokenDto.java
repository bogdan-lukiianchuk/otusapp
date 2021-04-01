package com.otus.homework.userserviceauth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.otus.homework.userserviceauth.utils.JsonUtils;

public class JwtTokenDto {
    @JsonProperty("token")
    private final String token;

    public JwtTokenDto(@JsonProperty("token") String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return JsonUtils.dump(this);
    }
}
