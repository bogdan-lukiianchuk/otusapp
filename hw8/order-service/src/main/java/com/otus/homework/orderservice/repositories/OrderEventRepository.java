package com.otus.homework.orderservice.repositories;

import com.otus.homework.orderservice.entities.OrderEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public interface OrderEventRepository extends JpaRepository<OrderEvent, UUID> {
    List<OrderEvent> findAllByOrderIdOrderByCreateTime(Long orderId);

    List<OrderEvent> findAllByUserIdOrderByOrderIdAscCreateTimeAsc(Long userId);

    @Query("select e from OrderEvent e where e.id in " +
            "(select o.id from OrderEvent o where o.type = 'CREATE' and o.createTime between :createdTimeStart and :createdTimeEnd) " +
            " order by e.id, e.createTime")
    List<OrderEvent> findAllByCreateTimeBetween(@Param("createdTimeStart") Instant createdTimeStart, @Param("createdTimeEnd") Instant createdTimeEnd);

    @Query("select e from OrderEvent e where e.id in " +
            "(select o.id from OrderEvent o where o.createTime = :createdTime ) " +
            " order by e.id, e.createTime")
    List<OrderEvent> findAllByCreateTime(@Param("createdTime") Instant createdTime);
}
