package com.otus.homework.warehouseservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Getter
@ToString
public class SupplyDto {
    @NotNull
    @JsonProperty("productId")
    private final Long productId;
    @NotNull
    @JsonProperty("count")
    private final Integer count;
}
