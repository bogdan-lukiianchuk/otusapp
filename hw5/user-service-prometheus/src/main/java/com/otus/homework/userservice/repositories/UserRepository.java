package com.otus.homework.userservice.repositories;

import com.otus.homework.userservice.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
