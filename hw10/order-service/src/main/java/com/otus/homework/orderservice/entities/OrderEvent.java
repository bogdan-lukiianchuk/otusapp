package com.otus.homework.orderservice.entities;

import com.otus.homework.orderservice.dto.UpdateOrderDto;
import com.otus.homework.orderservice.dto.rest.SetItemDto;
import com.otus.homework.orderservice.enums.EventType;
import com.otus.homework.orderservice.enums.OrderStatus;
import com.otus.homework.orderservice.interfaces.JsonData;
import com.otus.homework.orderservice.model.EmptyJson;
import com.otus.homework.orderservice.model.SetItemJson;
import com.otus.homework.orderservice.model.UpdateOrderJson;
import com.otus.homework.orderservice.model.UpdateOrderStatusJson;
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
public class OrderEvent {
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

    private OrderEvent(Long userId, Long orderId, Instant createTime, EventType type, JsonData jsonValue) {
        this.userId = userId;
        this.orderId = orderId;
        this.createTime = createTime;
        this.type = type;
        this.jsonValue = jsonValue;
    }

    public static OrderEvent createChangeStatusEvent(Long orderId, Long userId, OrderStatus orderStatus) {
        return new OrderEvent(
                userId,
                orderId,
                Instant.now(),
                EventType.CHANGE_STATUS,
                new UpdateOrderStatusJson(orderStatus));
    }

    public static OrderEvent createUpdateEvent(Long orderId, Long userId, UpdateOrderDto updateOrderDto) {
        return new OrderEvent(
                userId,
                orderId,
                Instant.now(),
                EventType.UPDATE,
                new UpdateOrderJson(updateOrderDto.getDeliveryTime(), updateOrderDto.getCourierName()));
    }

    public static OrderEvent createAddProductEvent(Long orderId, Long userId, SetItemDto setItemDto) {
        return new OrderEvent(
                userId,
                orderId,
                Instant.now(),
                EventType.ADD_PRODUCT,
                new SetItemJson(setItemDto.getName(), setItemDto.getCount()));
    }

    public static OrderEvent createEvent(Long orderId, Long userId) {
        return new OrderEvent(
                userId,
                orderId,
                Instant.now(),
                EventType.CREATE,
                new EmptyJson());
    }
}
