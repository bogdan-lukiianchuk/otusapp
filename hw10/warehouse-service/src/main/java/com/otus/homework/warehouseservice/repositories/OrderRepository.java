package com.otus.homework.warehouseservice.repositories;

import com.otus.homework.warehouseservice.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query(value = "select * from orders" +
            " where (status = 'CANCELED' or (status = 'CREATED' and reservation_end_time < now()))" +
            " and list_reservation_ids is not null", nativeQuery = true)
    List<Order> findAllExpiredOrCanceled();
}
