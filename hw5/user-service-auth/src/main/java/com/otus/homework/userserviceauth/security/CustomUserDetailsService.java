package com.otus.homework.userserviceauth.security;

import com.otus.homework.userserviceauth.entities.User;
import com.otus.homework.userserviceauth.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userRepository.findByLogin(userName).orElseThrow();
        if (user == null) {
            log.error("Пользователь %s не найден в БД {}", userName);
            throw new UsernameNotFoundException(String.format("Пользователь %s не найден в БД", userName));
        }
        return new UserDetailsImpl(user);
    }
}
