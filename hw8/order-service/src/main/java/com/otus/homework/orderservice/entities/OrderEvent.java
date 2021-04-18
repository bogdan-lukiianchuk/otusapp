package com.otus.homework.orderservice.entities;

import com.otus.homework.orderservice.enums.EventType;
import com.otus.homework.orderservice.interfaces.JsonData;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
@Table(name = "order_event")
@TypeDef(name = "json", typeClass = JsonBinaryType.class)
public class OrderEvent  {
    @Id
    @GeneratedValue
    @Column(name = "order_event_id")
    private UUID id;
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "order_id")
    private Long orderId;
    @Column(name = "create_time")
    private Instant createTime;
    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private EventType type;
    @Type(type = "json")
    @Column(columnDefinition = "json", name = "json_value")
    private JsonData jsonValue;

    public OrderEvent(Long userId, Long orderId, Instant createTime, EventType type, JsonData jsonValue) {
        this.userId = userId;
        this.orderId = orderId;
        this.createTime = createTime;
        this.type = type;
        this.jsonValue = jsonValue;
    }
}
