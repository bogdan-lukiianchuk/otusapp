package com.otus.homework.warehouseservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class CreatedReplenishmentDto {
    @JsonProperty("replenishmentId")
    private Long replenishmentId;
    @JsonProperty("replenishmentCode")
    private String replenishmentCode;
    @JsonProperty("supplies")
    private List<SupplyDto> supplies;
}
