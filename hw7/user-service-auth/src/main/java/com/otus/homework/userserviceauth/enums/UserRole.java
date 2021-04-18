package com.otus.homework.userserviceauth.enums;

public enum UserRole {
    ROLE_USER("пользователь"),
    ROLE_ADMIN("администратор");

    private String value;

    UserRole(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
