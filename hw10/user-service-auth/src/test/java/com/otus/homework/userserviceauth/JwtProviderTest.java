package com.otus.homework.userserviceauth;

import com.otus.homework.userserviceauth.security.JwtProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@SpringBootTest
class JwtProviderTest {
    @Autowired
    private JwtProvider jwtProvider;

    @Test
    void generateToken() {
        //eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ2ZXJhIiwiZXhwIjoxNjE3NDgzNjAwfQ.lb8v1F3ezyu7_6rXptWPGwOgiqbzvZbArf7tEDe5agAQTGZ7C4PjBdK3EXEZAHNa31ATmkY_51wmocIdWJ0kJw
        //eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ2ZXJhIiwiZXhwIjoxNjE3NDgzNjAwfQ.VnSN74-D2BICuWntKqZYtC6HkkaFiXvFH8C2Z2bf4y8
        String token = jwtProvider.generateToken(2L, "userService", "ROLE_ORDER_SRV");
        boolean validateToken = jwtProvider.validateToken(token);
        String loginFromToken = jwtProvider.getLoginFromToken(token);
        System.out.println(token);
        System.out.println(loginFromToken);
    }

    @Test
    void generateTokenUser() {
        //eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ2ZXJhIiwiZXhwIjoxNjE3NDgzNjAwfQ.lb8v1F3ezyu7_6rXptWPGwOgiqbzvZbArf7tEDe5agAQTGZ7C4PjBdK3EXEZAHNa31ATmkY_51wmocIdWJ0kJw
        //eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ2ZXJhIiwiZXhwIjoxNjE3NDgzNjAwfQ.VnSN74-D2BICuWntKqZYtC6HkkaFiXvFH8C2Z2bf4y8
        String token = jwtProvider.generateToken(2L, "vera", "ROLE_USER");
        boolean validateToken = jwtProvider.validateToken(token);
        String loginFromToken = jwtProvider.getLoginFromToken(token);
        System.out.println(token);
        System.out.println(loginFromToken);
    }

    @Test
    public void test() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println(encoder.encode("userService"));
        //System.out.println(encoder.encode("jerry"));
        System.out.println(new Date());
    }

    @Test
    void tt() {
        String s = "6cd76c92-d489-11e9-a8ce-6fc979c7a4c1-6cd76c92-d489-11e9-a8ce-6fc979c7a4c1";
        byte[] bytes = s.getBytes(StandardCharsets.UTF_8);
        System.out.println(bytes.length);

    }
}