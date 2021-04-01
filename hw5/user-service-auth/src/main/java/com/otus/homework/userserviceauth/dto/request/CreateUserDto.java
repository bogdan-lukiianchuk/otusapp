package com.otus.homework.userserviceauth.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class CreateUserDto {
    private final String login;
    private final String password;

    public CreateUserDto(@JsonProperty("login") String login,
                         @JsonProperty("password") String password) {
        this.login = login;
        this.password = password;
    }
}
