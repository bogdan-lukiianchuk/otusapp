package com.otus.homework.orderservice.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties("otusapp.billing")
public class ApplicationProperties {
    private Method method = new Method();
    private String baseUrl = "http://localhost:8080/api/accounts/";

    @Getter
    @Setter
    public static class Method {
        private String find = "find";
        private String operations = "operations";
    }
}
