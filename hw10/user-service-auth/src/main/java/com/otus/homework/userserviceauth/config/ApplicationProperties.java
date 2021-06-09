package com.otus.homework.userserviceauth.config;

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
    private String baseUrl;
    private String accessToken;

    @Getter
    @Setter
    public static class Method {
        private String create = "createSrv";
    }
}
