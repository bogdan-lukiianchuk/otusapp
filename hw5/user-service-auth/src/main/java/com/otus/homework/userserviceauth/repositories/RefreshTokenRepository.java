package com.otus.homework.userserviceauth.repositories;

import com.otus.homework.userserviceauth.entities.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, UUID> {
    void deleteAllByUserId(Long userId);
}
