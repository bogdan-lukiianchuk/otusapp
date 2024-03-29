package com.otus.homework.billingservice.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties("app")
public class ApplicationProperties {
    private Long maxAccountMoney = 1000000L;
    private Long minAccountMoney = 0L;
}
