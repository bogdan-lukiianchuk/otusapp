package com.otus.homework.orderservice.dto.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.otus.homework.orderservice.enums.OrderStages;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
public class OrderStageDto {
    @JsonProperty("orderId")
    private Long orderId;
    @JsonProperty("stage")
    private OrderStages stage;
    @JsonProperty("createTime")
    private LocalDateTime createTime;
}
