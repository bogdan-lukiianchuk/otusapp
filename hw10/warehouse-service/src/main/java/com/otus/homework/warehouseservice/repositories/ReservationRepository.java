package com.otus.homework.warehouseservice.repositories;

import com.otus.homework.warehouseservice.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findAllByIdIn(List<Long> ids);
}
