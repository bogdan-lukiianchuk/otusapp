package com.otus.homework.orderservice.security;

import com.otus.homework.orderservice.exceptions.ExpirationException;
import com.otus.homework.orderservice.exceptions.InvalidTokenException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class JwtFilter extends OncePerRequestFilter {
    private final JwtProvider jwtProvider;
    private static final String BEARER = "Bearer ";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        try {
            log.debug("auth header " + request.getHeader(HttpHeaders.AUTHORIZATION));
            log.debug("url = " + request.getRequestURI());

            if (request.getCookies() != null) {
                log.debug("cookie " + Arrays.stream(request.getCookies()).map(c -> c.getName() + " " + c.getValue()).collect(Collectors.joining(", ")));
            }
            String token = Optional.ofNullable(request.getHeader(HttpHeaders.AUTHORIZATION))
                    .map(this::cut)
                    .orElseThrow(() -> new InvalidTokenException("Не авторизован. Токен не найден."));
            Authentication auth = jwtProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(auth);
        } catch (InvalidTokenException e) {
            log.warn("Не авторизирован - невалидный формат токена", e);
        } catch (ExpirationException e) {
            log.info("Не авторизирован - истекло время жизни токена");
        } finally {
            filterChain.doFilter(request, response);
        }
    }

    private String cut(String token) {
        if (token.startsWith(BEARER)) {
            return token.substring(BEARER.length());
        }
        return token;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        log.debug("shouldNotFilter " + request.getHeader(HttpHeaders.AUTHORIZATION));
        log.debug("shouldNotFilter " + StringUtils.hasLength(request.getHeader(HttpHeaders.AUTHORIZATION)));
        boolean result = request.getHeader(HttpHeaders.AUTHORIZATION) == null;
        log.debug("shouldNotFilter " + result);
        return request.getHeader(HttpHeaders.AUTHORIZATION) == null;
    }
}
