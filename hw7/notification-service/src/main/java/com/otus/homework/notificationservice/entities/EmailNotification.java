package com.otus.homework.notificationservice.entities;

import com.otus.homework.notificationservice.enums.Type;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "email_notification")
public class EmailNotification {
    @Id
    @GeneratedValue
    @Column(name = "email_notification_id")
    private UUID id;
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "message")
    private String message;
    @Column(name = "send_time")
    private Instant sendTime;
    @Column(name = "order_number")
    private Long orderNumber;
    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private Type type;
}
