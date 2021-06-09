package com.otus.homework.orderservice.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties("otusapp.delivery")
public class ApplicationDeliveryProperties {
    private Method method = new Method();
    private String serviceUrl = "http://localhost:8080/api/delivery/";

    @Getter
    @Setter
    public static class Method {
        private String courier = "getCourier";
        private String cancel = "cancel";
    }
}
