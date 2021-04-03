package com.otus.homework.userservice.controllers;

import com.otus.homework.userservice.dto.UserDto;
import com.otus.homework.userservice.services.UserService;
import com.otus.homework.userservice.services.UserSessionService;
import io.micrometer.core.annotation.Timed;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/")
@AllArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserSessionService userSessionService;

    @Timed
    @GetMapping
    public UserDto me() {
        return userService.getCreatedUser(userSessionService.getUser());
    }

    @Timed
    @PatchMapping
    public UserDto update(@RequestBody UserDto userDto) {
        userDto.setId(userSessionService.getUserId());
        return userService.update(userDto);
    }

    @Timed
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        userService.delete(id);
    }
}
