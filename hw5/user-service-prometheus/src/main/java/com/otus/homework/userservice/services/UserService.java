package com.otus.homework.userservice.services;

import com.otus.homework.userservice.dto.UserDto;
import com.otus.homework.userservice.entities.User;
import com.otus.homework.userservice.repositories.UserRepository;
import com.otus.homework.userservice.security.AuthUser;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserAuthIntegrationService integrationService;

    @Transactional
    public UserDto getCreatedUser(AuthUser authUser) {
        Optional<User> userOpt = userRepository.findById(authUser.getId());
        User user = userOpt.isEmpty() ? create(authUser) : userOpt.get();
        UserDto result = new UserDto();
        BeanUtils.copyProperties(user, result);
        return result;
    }

    @Transactional
    public UserDto update(UserDto userDto) {
        User user = userRepository.findById(userDto.getId()).orElseThrow();
        Optional.ofNullable(userDto.getEmail()).ifPresent(user::setEmail);
        Optional.ofNullable(userDto.getLogin()).ifPresent(user::setLogin);
        Optional.ofNullable(userDto.getPhoneNumber()).ifPresent(user::setPhoneNumber);
        User saved = userRepository.save(user);
        integrationService.update(userDto);
        UserDto result = new UserDto();
        BeanUtils.copyProperties(saved, result);
        return result;
    }

    @Transactional
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    private User create(AuthUser authUser) {
        User user = new User();
        user.setId(authUser.getId());
        user.setLogin(authUser.getName());
        return userRepository.save(user);
    }
}
