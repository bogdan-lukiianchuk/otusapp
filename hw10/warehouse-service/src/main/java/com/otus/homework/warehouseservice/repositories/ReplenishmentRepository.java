package com.otus.homework.warehouseservice.repositories;

import com.otus.homework.warehouseservice.entities.Replenishment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplenishmentRepository extends JpaRepository<Replenishment, Long> {
}
