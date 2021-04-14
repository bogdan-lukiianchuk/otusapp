package com.otus.homework.userserviceauth.controllers;

import com.otus.homework.userserviceauth.UserDtoMapper;
import com.otus.homework.userserviceauth.dto.UserPasswordDto;
import com.otus.homework.userserviceauth.dto.request.CreateUserDto;
import com.otus.homework.userserviceauth.dto.response.UserDto;
import com.otus.homework.userserviceauth.services.UserService;
import com.otus.homework.userserviceauth.services.UserSessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class UserController {
    private final UserService userService;
    private final UserDtoMapper userDtoMapper;
    private final UserSessionService userSessionService;

    @PostMapping("/updateUser")
    @PreAuthorize("hasRole('ROLE_SRV')")
    public ResponseEntity<UserDto> update(@RequestBody UserDto userDto) {
        return ResponseEntity.ok(userDtoMapper.map(userService.update( userDto)));
    }

    @PostMapping("/changePassword")
    public ResponseEntity<UserDto> changePassword(@RequestBody UserPasswordDto userDto) {
        Long id = userSessionService.getUser().getId();
        return ResponseEntity.ok(userDtoMapper.map(userService.changePassword(id, userDto)));
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody CreateUserDto userDto) {
        return ResponseEntity.ok(userDtoMapper.map(userService.register(userDto)));
    }

    @GetMapping
    public ResponseEntity<UserDto> get() {
        Long id = userSessionService.getUser().getId();
        return ResponseEntity.ok(userDtoMapper.map(userService.get(id)));
    }
}
