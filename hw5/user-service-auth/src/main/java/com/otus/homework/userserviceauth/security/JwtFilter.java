package com.otus.homework.userserviceauth.security;

import com.otus.homework.userserviceauth.exceptions.InvalidTokenException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class JwtFilter extends OncePerRequestFilter {
    private final JwtProvider jwtProvider;
    private static final String BEARER = "Bearer ";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        String token = Optional.ofNullable(request.getHeader(HttpHeaders.AUTHORIZATION))
                .map(this::cut)
                .orElseThrow(() -> new InvalidTokenException("Не авторизован. Токен не найден."));
        if (token != null && jwtProvider.validateToken(token)) {
            String userLogin = jwtProvider.getLoginFromToken(token);
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                    new AuthUser(jwtProvider.getUserId(token), userLogin),
                    null,
                    jwtProvider.getUserAuthorities(token));
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        filterChain.doFilter(request, response);
    }

    private String cut(String token) {
        if (token.startsWith(BEARER)) {
            return token.substring(BEARER.length());
        }
        return token;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return request.getHeader(HttpHeaders.AUTHORIZATION) == null;
    }
}
