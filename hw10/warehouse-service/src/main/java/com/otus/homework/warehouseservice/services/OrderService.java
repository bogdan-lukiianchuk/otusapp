package com.otus.homework.warehouseservice.services;

import com.otus.homework.warehouseservice.config.WarehouseProperties;
import com.otus.homework.warehouseservice.dto.OrderDto;
import com.otus.homework.warehouseservice.dto.ReservationDto;
import com.otus.homework.warehouseservice.dto.SupplyDto;
import com.otus.homework.warehouseservice.entities.Order;
import com.otus.homework.warehouseservice.entities.Reservation;
import com.otus.homework.warehouseservice.entities.Supply;
import com.otus.homework.warehouseservice.enums.OrderStatus;
import com.otus.homework.warehouseservice.exceptions.ReservationException;
import com.otus.homework.warehouseservice.repositories.OrderRepository;
import com.otus.homework.warehouseservice.repositories.ReservationRepository;
import com.otus.homework.warehouseservice.repositories.SupplyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrderService {
    public static final String RESERVATION_ERROR_MESSAGE = "Ошибка резервирования, отсутствует товар на складе.";
    private final OrderRepository orderRepository;
    private final SupplyRepository supplyRepository;
    private final ReservationRepository reservationRepository;
    private final WarehouseProperties properties;

    @Transactional
    public void toTransfer(OrderDto orderDto) {
        Order order = orderRepository.findById(orderDto.getOrderId()).orElseThrow();
        if (order.getStatus() != OrderStatus.CREATED) {
            throw new UnsupportedOperationException("Нельзя выполнить заказ. Статус заказа " + order.getStatus().getValue());
        }
        order.setStatus(OrderStatus.DONE);
    }

    @Transactional
    public void cancel(OrderDto orderDto) {
        Order order = orderRepository.findById(orderDto.getOrderId()).orElseThrow();
        if (order.getStatus() == OrderStatus.CANCELED || order.getStatus() == OrderStatus.EXPIRED){
            log.info("Заказ {} не отменен так как статус заказа {}", order.getId(), order.getStatus());
            return;
        }
        if (order.getStatus() != OrderStatus.CREATED) {
            throw new UnsupportedOperationException("Нельзя отменить заказ. Статус заказа " + order.getStatus().getValue());
        }
        order.setStatus(OrderStatus.CANCELED);
    }

    @Transactional
    public void reserve(ReservationDto reservationDto) {
        if(orderRepository.existsById(reservationDto.getOrderId())){
            throw new UnsupportedOperationException("заказ уже был обработан");
        }
        Map<Long, Supply> supplyMap = getSupplies(reservationDto);
        List<Reservation> reservations = createReservations(reservationDto, supplyMap);
        List<Long> reservationIds = reservationRepository.saveAll(reservations)
                .stream()
                .map(Reservation::getId)
                .collect(Collectors.toList());
        Order order = new Order();
        order.setId(reservationDto.getOrderId());
        order.setCreatedTime(Instant.now());
        order.setStatus(OrderStatus.CREATED);
        order.setReservationIds(reservationIds);
        order.setReservationEndTime(order.getCreatedTime().plus(properties.getReservationTime(), ChronoUnit.MINUTES));
        orderRepository.save(order);
    }

    private Map<Long, Supply> getSupplies(ReservationDto reservationDto) {
        Map<Long, Supply> supplyMap = new HashMap<>();
        List<Long> productIds = reservationDto
                .getReservations()
                .stream()
                .map(SupplyDto::getProductId)
                .collect(Collectors.toList());
        supplyRepository.selectForUpdate(productIds)
                .forEach(v -> supplyMap.put(v.getId(), v));
        return supplyMap;
    }

    private List<Reservation> createReservations(ReservationDto reservationDto, Map<Long, Supply> warehouseMap) {
        List<Reservation> reservations = new ArrayList<>();
        reservationDto.getReservations()
                .forEach(supplyDto -> {
                    Supply supplyItem = warehouseMap.get(supplyDto.getProductId());
                    if (supplyItem == null) {
                        throw new ReservationException(RESERVATION_ERROR_MESSAGE);
                    }
                    int result = supplyItem.getCount() - supplyDto.getCount();
                    if (result < 0) {
                        throw new ReservationException(RESERVATION_ERROR_MESSAGE);
                    }
                    supplyItem.setCount(result);
                    Reservation reservation = new Reservation();
                    reservation.setCount(supplyDto.getCount());
                    reservation.setSupplyId(supplyDto.getProductId());
                    reservations.add(reservation);
                });
        return reservations;
    }
}
