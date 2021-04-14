package com.otus.homework.userserviceauth.security;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MethodSecurityConfiguration extends GlobalMethodSecurityConfiguration {
    private final PermissionService permissionService;
    private final ApplicationContext context;

    public MethodSecurityConfiguration(PermissionService permissionService, ApplicationContext context) {
        this.permissionService = permissionService;
        this.context = context;
    }

    @Override
    protected MethodSecurityExpressionHandler createExpressionHandler() {
        DefaultMethodSecurityExpressionHandler expressionHandler = new CustomMethodSecurityExpressionHandler(permissionService);
        expressionHandler.setApplicationContext(context);
        return expressionHandler;
    }
}
