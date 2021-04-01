package com.otus.homework.userserviceauth.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserDto {
    @JsonProperty
    private final Long id;
    @JsonProperty
    private final String login;
}
