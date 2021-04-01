package com.otus.homework.userserviceauth.controllers;

import com.otus.homework.userserviceauth.dto.request.AuthRequestDto;
import com.otus.homework.userserviceauth.dto.response.AuthResponseDto;
import com.otus.homework.userserviceauth.exceptions.InvalidTokenException;
import com.otus.homework.userserviceauth.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.stream.Stream;


@RequiredArgsConstructor
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class AuthUserController {
    private final AuthenticationService authenticationService;
    private static final String REFRESH_COOKIE = "refresh";

    @PostMapping("/login")
    public void auth(@RequestBody AuthRequestDto request, HttpServletResponse response) {
        AuthResponseDto auth = authenticationService.auth(request);
        addCookie(response, auth.getRefreshToken());
        addCookieAuth(response, auth.getToken());
       // response.setHeader(HttpHeaders.AUTHORIZATION, auth.getToken());
        response.setStatus(HttpServletResponse.SC_OK);
    }

    @PostMapping("/signin")
    public void signin(@RequestBody AuthRequestDto request, HttpServletResponse response) {
        AuthResponseDto auth = authenticationService.auth(request);
        addCookie(response, auth.getRefreshToken());
        addCookieAuth(response, auth.getToken());
        response.setHeader(HttpHeaders.AUTHORIZATION, auth.getToken());
        response.setStatus(HttpServletResponse.SC_OK);
    }

    @GetMapping("/refresh")
    public void refresh(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        String token = Stream.of(cookies)
                .filter(c -> c.getName().equals(REFRESH_COOKIE))
                .map(Cookie::getValue)
                .findFirst()
                .orElseThrow(() -> new InvalidTokenException("Токен отсутствует"));
        AuthResponseDto auth = authenticationService.refresh(token);
        addCookie(response, auth.getRefreshToken());
        addCookieAuth(response, auth.getToken());
      //  response.setHeader(HttpHeaders.AUTHORIZATION, auth.getToken());
        response.setStatus(HttpServletResponse.SC_OK);
    }

    private void addCookie(HttpServletResponse response, String refreshToken) {
        Cookie cookie = new Cookie(REFRESH_COOKIE, refreshToken);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
    }

    private void addCookieAuth(HttpServletResponse response, String token) {
        Cookie cookie = new Cookie(HttpHeaders.AUTHORIZATION, token);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
    }
}
