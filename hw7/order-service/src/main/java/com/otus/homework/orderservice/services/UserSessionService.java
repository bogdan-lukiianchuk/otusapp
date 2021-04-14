package com.otus.homework.orderservice.services;

import com.otus.homework.orderservice.security.AuthUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserSessionService {

    public AuthUser getUser() {
        return (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public Long getUserId() {
        return getUser().getId();
    }
}
