package com.otus.homework.notificationservice.controllers;

import com.otus.homework.notificationservice.EmailNotificationMapper;
import com.otus.homework.notificationservice.dto.EmailNotificationDto;
import com.otus.homework.notificationservice.services.EmailNotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/email")
public class EmailNotificationController {
    private final EmailNotificationService emailNotificationService;
    private final EmailNotificationMapper emailNotificationMapper;

    @GetMapping("/")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<EmailNotificationDto> getAll() {
        return emailNotificationService.getAll().stream()
                .map(emailNotificationMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/notification")
    public List<EmailNotificationDto> getAllForUser() {
        return emailNotificationService.getAllForUser().stream()
                .map(emailNotificationMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping(value = "/", params = "userId")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<EmailNotificationDto> getAllByUserId(@RequestParam("userId") Long userId) {
        return emailNotificationService.getAllByUserId(userId).stream()
                .map(emailNotificationMapper::toDto)
                .collect(Collectors.toList());
    }
}
