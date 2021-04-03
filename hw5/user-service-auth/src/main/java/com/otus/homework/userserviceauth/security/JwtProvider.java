package com.otus.homework.userserviceauth.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.SecurityException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.time.temporal.ChronoUnit.MINUTES;

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

    public String generateToken(Long id, String login, String authorities) {
        Date date = Date.from(Instant.now().plus(15, MINUTES));
        Claims claims = Jwts.claims().setSubject(id.toString());
        claims.put(LOGIN_CLAIM, login);
        claims.put(AUTH_CLAIM, String.join(",", authorities));
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(date)
                .setIssuedAt(new Date())
                .signWith(secretKey)
                .compact();
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

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            log.error("Token expired", e);
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported jwt", e);
        } catch (MalformedJwtException e) {
            log.error("Malformed jwt", e);
        } catch (SecurityException e) {
            log.error("Invalid signature", e);
        } catch (Exception e) {
            log.error("invalid token", e);
        }
        return false;
    }

    public String getLoginFromToken(String token) {
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
