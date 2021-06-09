package com.otus.homework.orderservice.repositories;

import com.otus.homework.orderservice.entities.OrderStage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrderStageRepository extends JpaRepository<OrderStage, Long> {

    @Query(value = "select * from order_stage where order_id = :orderId for update", nativeQuery = true)
    Optional<OrderStage> getForUpdate(@Param("orderId") Long orderId);

    @Query(value = "select * from order_stage where stage = :stage", nativeQuery = true)
    List<OrderStage> getAllByStage(@Param("stage") String stage);

    List<OrderStage> findAllByOrderIdOrderByCreateTime(Long orderId);
}
