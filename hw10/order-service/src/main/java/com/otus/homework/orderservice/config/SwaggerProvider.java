package com.otus.homework.orderservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Primary
public class SwaggerProvider implements SwaggerResourcesProvider {
    private final Map<String, String> servicesNames;

    public SwaggerProvider(@Value("#{${swagger.services.names}}") Map<String, String> servicesNames) {
        this.servicesNames = servicesNames;
    }

    //http://localhost:8085/../swagger/v2/api-docs
    @Override
    public List<SwaggerResource> get() {
        String apiDocs = "v2/api-docs";
        return servicesNames
                .entrySet()
                .stream()
                .map(it -> swaggerResource(it.getKey(), "/../" + it.getValue() + apiDocs))
                .collect(Collectors.toList());
    }

    private SwaggerResource swaggerResource(String name, String location) {
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setUrl(location);
        return swaggerResource;
    }
}
