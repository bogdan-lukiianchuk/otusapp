package com.otus.homework.orderservice.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "order_pay_idempotence_operation")
public class OrderPayIdempotenceOperation {
    @Id
    @Column(name = "order_id")
    private Long orderId;
    @Column(name = "key")
    private UUID key;
    @Column(name = "account_id")
    private Long accountId;
}
