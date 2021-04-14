package com.otus.homework.billingservice.repositories;

import com.otus.homework.billingservice.entities.OperationLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OperationLogRepository extends JpaRepository<OperationLog, UUID> {
}
