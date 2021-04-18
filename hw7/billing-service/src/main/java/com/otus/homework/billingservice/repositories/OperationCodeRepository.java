package com.otus.homework.billingservice.repositories;

import com.otus.homework.billingservice.entities.OperationCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OperationCodeRepository extends JpaRepository<OperationCode, Long> {
    Optional<OperationCode> findByOperation(String operation);
}
