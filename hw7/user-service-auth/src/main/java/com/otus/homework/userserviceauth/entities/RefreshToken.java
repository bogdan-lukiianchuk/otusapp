package com.otus.homework.userserviceauth.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "refresh_token")
public class RefreshToken {
    @Id
    @Column(name = "refresh_token_id", columnDefinition = "uuid", updatable = false)
    @GeneratedValue
    private UUID id;
    private Instant expirationTime;
    @Column(name = "app_user_id")
    private Long userId;
    @OneToOne
    @JoinColumn(name = "app_user_id", updatable = false, insertable = false)
    private User user;
}
