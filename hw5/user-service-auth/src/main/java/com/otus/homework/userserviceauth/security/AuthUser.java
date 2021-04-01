package com.otus.homework.userserviceauth.security;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.security.Principal;

@AllArgsConstructor
@Getter
public class AuthUser implements Principal {
    private final Long id;
    private final String name;
}
