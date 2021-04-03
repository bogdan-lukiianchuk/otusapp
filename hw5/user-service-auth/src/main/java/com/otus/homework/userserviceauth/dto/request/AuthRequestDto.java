package com.otus.homework.userserviceauth.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AuthRequestDto {
    private final String login;
    private final String password;

    public AuthRequestDto(@JsonProperty("login") String login,
                          @JsonProperty("password") String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}
