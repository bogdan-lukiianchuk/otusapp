package com.otus.homework.userserviceauth.services;

import com.otus.homework.userserviceauth.security.AuthUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserSessionService {

    public AuthUser getUser() {
        return (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
