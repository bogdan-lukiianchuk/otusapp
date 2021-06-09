package com.otus.homework.userserviceauth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class UserPasswordDto {
    @JsonProperty
    private String password;
}
