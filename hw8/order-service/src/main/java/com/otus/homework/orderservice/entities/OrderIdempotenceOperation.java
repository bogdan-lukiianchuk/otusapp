package com.otus.homework.orderservice.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "order_idempotence_operation")
public class OrderIdempotenceOperation {
    @Id
    @Column(name = "order_idempotence_operation_id")
    private UUID id;
    @Column(name = "order_id")
    private Long orderId;
}
