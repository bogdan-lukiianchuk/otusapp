package com.otus.homework.warehouseservice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("warehouse")
public class WarehouseProperties {
    private Integer reservationTime = 15;
}
