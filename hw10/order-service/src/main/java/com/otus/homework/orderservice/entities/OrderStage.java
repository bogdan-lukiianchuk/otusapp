package com.otus.homework.orderservice.entities;

import com.otus.homework.orderservice.enums.OrderStages;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "order_stage")
public class OrderStage {
    @Id
    @Column(name = "order_id")
    private Long orderId;
    @Column(name = "stage")
    @Enumerated(EnumType.STRING)
    private OrderStages stage;
    @Column(name = "create_time")
    private LocalDateTime createTime;
}
