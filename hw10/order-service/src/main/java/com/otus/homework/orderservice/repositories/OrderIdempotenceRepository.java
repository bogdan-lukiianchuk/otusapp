package com.otus.homework.orderservice.repositories;

import com.otus.homework.orderservice.entities.OrderIdempotenceOperation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderIdempotenceRepository extends JpaRepository<OrderIdempotenceOperation, UUID> {
}
