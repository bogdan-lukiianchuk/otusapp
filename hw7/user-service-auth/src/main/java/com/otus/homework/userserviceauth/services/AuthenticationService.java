package com.otus.homework.userserviceauth.services;

import com.otus.homework.userserviceauth.dto.request.AuthRequestDto;
import com.otus.homework.userserviceauth.dto.response.AuthResponseDto;
import com.otus.homework.userserviceauth.entities.RefreshToken;
import com.otus.homework.userserviceauth.entities.User;
import com.otus.homework.userserviceauth.exceptions.InvalidTokenException;
import com.otus.homework.userserviceauth.repositories.RefreshTokenRepository;
import com.otus.homework.userserviceauth.security.JwtProvider;
import com.otus.homework.userserviceauth.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.time.temporal.ChronoUnit.HOURS;

@Slf4j
@Setter
@RequiredArgsConstructor
@Service
public class AuthenticationService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtProvider jwtProvider;
    private final AuthenticationManager authenticationManager;
    @Value("${token.refresh.expiration-time:12}")
    private Integer expirationTime;

    @Transactional
    public AuthResponseDto refresh(String token) {
        UUID id = UUID.fromString(token);
        RefreshToken refreshToken = refreshTokenRepository.findById(id)
                .orElseThrow(() -> new InvalidTokenException("Не найден токен " + token));
        User user = refreshToken.getUser();
        if (refreshToken.getExpirationTime().isBefore(Instant.now())) {
            log.error("Токен истек");
            throw new InvalidTokenException("Токен истек");
        }
        refreshTokenRepository.delete(refreshToken);
        refreshTokenRepository.flush();
        return new AuthResponseDto(jwtProvider.generateToken(user.getId(), user.getLogin(), user.getRole().toString()),
                createRefreshToken(user.getId()));
    }

    @Transactional
    public AuthResponseDto auth(AuthRequestDto request) {
        Authentication authenticate;
        try {
            authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getLogin(), request.getPassword()));
        } catch (BadCredentialsException ex) {
            log.error("Логин или пароль указаны неверно", ex);
            throw new BadCredentialsException("Логин или пароль указаны неверно", ex);
        } catch (AuthenticationException ex) {
            log.error("Ошибка аутентификации", ex);
            throw new RuntimeException("Ошибка аутентификации", ex);
        }
        UserDetailsImpl principal = (UserDetailsImpl) authenticate.getPrincipal();
        refreshTokenRepository.deleteAllByUserId(principal.getId());
        refreshTokenRepository.flush();
        String refreshToken = createRefreshToken(principal.getId());
        String auth = principal.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        return new AuthResponseDto(jwtProvider.generateToken(principal.getId(), principal.getUsername(), auth), refreshToken);
    }

    private String createRefreshToken(Long userId) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setExpirationTime(Instant.now().plus(expirationTime, HOURS));
        refreshToken.setUserId(userId);
        return refreshTokenRepository.save(refreshToken).getId().toString();
    }
}
