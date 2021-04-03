package com.otus.homework.userservice.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class JwtProvider {
    private final SecretKeySpec secretKey;
    private static final String LOGIN_CLAIM = "login";
    private static final String AUTH_CLAIM = "auth";

    public JwtProvider(@Value("${jwt.secret}") String base64Secret) {
        secretKey = new SecretKeySpec(
                Decoders.BASE64.decode(base64Secret),
                SignatureAlgorithm.HS512.getJcaName());
    }

    Authentication getAuthentication(String token) {
        return new UsernamePasswordAuthenticationToken(
                new AuthUser(getUserId(token), getLogin(token)),
                null,
                getUserAuthorities(token));
    }

    public List<GrantedAuthority> getUserAuthorities(String token) {
        String authorities =
                Optional.ofNullable(
                        Jwts.parserBuilder()
                                .setSigningKey(secretKey)
                                .build()
                                .parseClaimsJws(token)
                                .getBody()
                                .get(AUTH_CLAIM, String.class))
                        .orElseThrow();
        return Arrays.stream(authorities.split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    public String getLogin(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.get(LOGIN_CLAIM, String.class);
    }

    public Long getUserId(String token) {
        String idString = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
        return Long.parseLong(idString);
    }
}
