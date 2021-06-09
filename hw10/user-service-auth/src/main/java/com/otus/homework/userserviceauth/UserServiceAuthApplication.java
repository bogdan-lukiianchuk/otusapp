package com.otus.homework.userserviceauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties
@SpringBootApplication
public class UserServiceAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserServiceAuthApplication.class, args);
    }

}
