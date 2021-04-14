package com.otus.homework.notificationservice.services;

import com.otus.homework.notificationservice.dto.EmailNotificationTaskDto;
import com.otus.homework.notificationservice.entities.EmailNotification;
import com.otus.homework.notificationservice.enums.Type;
import com.otus.homework.notificationservice.repositories.EmailNotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@RequiredArgsConstructor
@Service
public class EmailNotificationService {
    private final EmailNotificationRepository emailNotificationRepository;
    private final UserSessionService userSessionService;

    @Transactional
    public void sendEmail(EmailNotificationTaskDto notification) {
        EmailNotification emailNotification = new EmailNotification();
        emailNotification.setSendTime(Instant.now());
        emailNotification.setUserId(notification.getUserId());
        emailNotification.setType(notification.getType());
        emailNotification.setOrderNumber(notification.getOrderNumber());
        if (notification.getType() == Type.ORDER_CREATED) {
            emailNotification.setMessage(
                    String.format("%s, заказ #%s успешно оплачен.",
                            notification.getLogin(), notification.getOrderNumber()));
        } else {
            emailNotification.setMessage(
                    String.format("%s, оплата заказа #%s не проведена. Причина: %s",
                            notification.getLogin(), notification.getOrderNumber(), notification.getMessage()));
        }
        emailNotificationRepository.save(emailNotification);
    }

    @Transactional(readOnly = true)
    public List<EmailNotification> getAll(){
        return emailNotificationRepository.findAll(Sort.by(Sort.Direction.DESC, "sendTime"));
    }

    @Transactional(readOnly = true)
    public List<EmailNotification> getAllByUserId(Long userId) {
        return emailNotificationRepository.findAllByUserIdOrderBySendTimeDesc(userId);
    }

    @Transactional(readOnly = true)
    public List<EmailNotification> getAllForUser() {
        Long userId = userSessionService.getUserId();
        return emailNotificationRepository.findAllByUserIdOrderBySendTimeDesc(userId);
    }
}
