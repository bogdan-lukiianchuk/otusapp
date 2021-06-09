package com.otus.homework.orderservice.repositories;

import com.otus.homework.orderservice.entities.OrderPayIdempotenceOperation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderPayIdempotenceOperationRepository extends JpaRepository<OrderPayIdempotenceOperation, Long> {
}
