package com.otus.homework.warehouseservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@AllArgsConstructor
@Getter
@ToString
public class ReplenishmentDto {
    @NotNull
    @JsonProperty("replenishmentCode")
    private final String replenishmentCode;
    @NotEmpty
    @JsonProperty("supplies")
    private final List<SupplyDto> supplies;
}
