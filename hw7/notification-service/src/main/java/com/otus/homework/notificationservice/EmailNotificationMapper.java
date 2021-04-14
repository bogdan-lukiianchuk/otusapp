package com.otus.homework.notificationservice;

import com.otus.homework.notificationservice.dto.EmailNotificationDto;
import com.otus.homework.notificationservice.entities.EmailNotification;
import org.springframework.stereotype.Service;

@Service
public class EmailNotificationMapper {
    public EmailNotificationDto toDto(EmailNotification entity) {
        return new EmailNotificationDto(
                entity.getId(), entity.getUserId(), entity.getMessage(),
                entity.getSendTime(), entity.getOrderNumber(), entity.getType());
    }
}
