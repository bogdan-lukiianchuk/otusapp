package com.otus.homework.warehouseservice.entities;

import com.otus.homework.warehouseservice.enums.OrderStatus;
import com.vladmihalcea.hibernate.type.array.ListArrayType;
import lombok.Data;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;

@Entity
@Data
@TypeDef(name = "list-array", typeClass = ListArrayType.class)
@Table(name = "orders")
public class Order {
    @Id
    @Column(name = "order_id")
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private OrderStatus status;
    @Type(type = "list-array")
    @Column(columnDefinition = "bigint[]", name = "list_reservation_ids")
    private List<Long> reservationIds;
    @Column(name = "created_time")
    private Instant createdTime;
    @Column(name = "reservation_end_time")
    private Instant reservationEndTime;
}
