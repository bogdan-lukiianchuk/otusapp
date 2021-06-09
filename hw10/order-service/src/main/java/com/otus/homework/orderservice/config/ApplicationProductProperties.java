package com.otus.homework.orderservice.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties("otusapp.product")
public class ApplicationProductProperties {
    private Method method = new Method();
    private String serviceUrl = "http://localhost:8083/api/products/";

    @Getter
    @Setter
    public static class Method {
        private String list = "list";
    }
}
