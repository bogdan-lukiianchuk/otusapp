package com.otus.homework.orderservice.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties("otusapp.billing")
public class ApplicationBillingProperties {
    private Method method = new Method();
    private String accountServiceUrl = "http://localhost:8080/api/accounts/";

    @Getter
    @Setter
    public static class Method {
        private String find = "find";
        private String operations = "operations";
        private String cancel = "operations/cancel";
    }
}
