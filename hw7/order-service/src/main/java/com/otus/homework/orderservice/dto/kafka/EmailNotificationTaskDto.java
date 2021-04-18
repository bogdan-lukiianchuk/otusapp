package com.otus.homework.orderservice.dto.kafka;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.otus.homework.orderservice.enums.Type;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EmailNotificationTaskDto {
    @JsonProperty("userId")
    private Long userId;
    @JsonProperty("login")
    private String login;
    @JsonProperty("type")
    private Type type;
    @JsonProperty("orderNumber")
    private Long orderNumber;
    @JsonProperty("message")
    private String message;
}
