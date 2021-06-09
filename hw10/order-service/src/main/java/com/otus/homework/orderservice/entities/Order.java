package com.otus.homework.orderservice.entities;

import com.otus.homework.orderservice.dto.rest.OrderDto;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "orders")
@TypeDef(name = "json", typeClass = JsonBinaryType.class)
public class Order {
    @Id
    @Column(name = "order_id")
    private Long id;
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "last_update")
    private Instant lastUpdate;
    @Type(type = "json")
    @Column(columnDefinition = "json", name = "data")
    private OrderDto data;
}
