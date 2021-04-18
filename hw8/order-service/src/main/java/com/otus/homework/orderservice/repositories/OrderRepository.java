package com.otus.homework.orderservice.repositories;

import com.otus.homework.orderservice.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
