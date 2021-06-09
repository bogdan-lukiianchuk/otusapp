package com.otus.homework.orderservice.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties("otusapp.warehouse")
public class ApplicationWarehouseProperties {
    private Method method = new Method();
    private String serviceUrl = "http://localhost:8080/api/warehouse/";

    @Getter
    @Setter
    public static class Method {
        private String reserve = "orders/reserve";
        private String cancel = "orders/cancel";
        private String transfer = "orders/transfer";
    }
}
