package com.otus.homework.notificationservice.repositories;

import com.otus.homework.notificationservice.entities.EmailNotification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface EmailNotificationRepository extends JpaRepository<EmailNotification, UUID> {
    List<EmailNotification> findAllByUserIdOrderBySendTimeDesc(Long userId);
}
