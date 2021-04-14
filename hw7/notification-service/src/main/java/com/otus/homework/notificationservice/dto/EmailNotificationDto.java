package com.otus.homework.notificationservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.otus.homework.notificationservice.enums.Type;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.Instant;
import java.util.UUID;

@ToString
@Getter
@AllArgsConstructor
public class EmailNotificationDto {
    @JsonProperty("id")
    private final UUID id;
    @JsonProperty("userId")
    private final Long userId;
    @JsonProperty("message")
    private final String message;
    @JsonProperty("sendTime")
    private final Instant sendTime;
    @JsonProperty("orderNumber")
    private final Long orderNumber;
    @JsonProperty("type")
    private final Type type;
}
