package com.otus.homework.orderservice.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties("otusapp.access")
public class ApplicationAccessProperties {
    private String tokenOrderSrv;
}
