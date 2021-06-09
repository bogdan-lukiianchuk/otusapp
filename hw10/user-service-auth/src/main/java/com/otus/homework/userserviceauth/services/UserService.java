package com.otus.homework.userserviceauth.services;

import com.otus.homework.userserviceauth.dto.UserPasswordDto;
import com.otus.homework.userserviceauth.dto.request.CreateUserDto;
import com.otus.homework.userserviceauth.dto.response.UserDto;
import com.otus.homework.userserviceauth.entities.User;
import com.otus.homework.userserviceauth.enums.UserRole;
import com.otus.homework.userserviceauth.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;
    private final BillingIntegrationService billingIntegrationService;

    public User get(Long id) {
        return userRepository.findById(id).orElseThrow();
    }

    @Transactional
    public User register(CreateUserDto userDto) {
        User user = new User();
        user.setLogin(userDto.getLogin());
        user.setRole(UserRole.ROLE_USER);
        user.setPassword(encoder.encode(userDto.getPassword()));
        User saved = userRepository.save(user);
        billingIntegrationService.createAccount(saved.getId());
        return saved;
    }

    @Transactional
    public User update(UserDto userDto) {
        User user = userRepository.findById(userDto.getId()).orElseThrow();
        Optional.ofNullable(userDto.getLogin())
                .ifPresent(user::setLogin);
        return userRepository.save(user);
    }

    @Transactional
    public User changePassword(Long id, UserPasswordDto passwordDto) {
        User user = userRepository.findById(id).orElseThrow();
        user.setPassword(encoder.encode(passwordDto.getPassword()));
        return userRepository.save(user);
    }
}
