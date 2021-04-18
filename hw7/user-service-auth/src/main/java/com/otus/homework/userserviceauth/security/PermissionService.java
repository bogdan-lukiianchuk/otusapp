package com.otus.homework.userserviceauth.security;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class PermissionService {

    @Transactional
    public boolean canUpdateUser(UUID id, AuthUser user) {
        return user.getId().equals(id);
    }
}
