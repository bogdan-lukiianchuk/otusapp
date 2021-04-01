package com.otus.homework.userserviceauth.security;

import com.otus.homework.userserviceauth.entities.User;
import com.otus.homework.userserviceauth.enums.UserRole;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public class UserDetailsImpl implements UserDetails  {
    @Getter
    private final Long id;
    @Getter
    private final UserRole role;
    private final String login;
    private final String password;
    private final boolean isAccountExpired = false;

    public UserDetailsImpl(User user) {
        this.id = user.getId();
        this.login = user.getLogin();
        this.password = user.getPassword();
        this.role = user.getRole();
    }

    @Override
    public List<GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return !isAccountExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !isAccountExpired;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
