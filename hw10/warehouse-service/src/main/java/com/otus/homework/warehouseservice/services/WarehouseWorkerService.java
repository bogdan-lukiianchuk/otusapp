package com.otus.homework.warehouseservice.services;

import com.otus.homework.warehouseservice.entities.Order;
import com.otus.homework.warehouseservice.entities.Reservation;
import com.otus.homework.warehouseservice.entities.Supply;
import com.otus.homework.warehouseservice.enums.OrderStatus;
import com.otus.homework.warehouseservice.repositories.OrderRepository;
import com.otus.homework.warehouseservice.repositories.ReservationRepository;
import com.otus.homework.warehouseservice.repositories.SupplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class WarehouseWorkerService {
    private final OrderRepository orderRepository;
    private final SupplyRepository supplyRepository;
    private final ReservationRepository reservationRepository;

    @Transactional
    @Scheduled(fixedDelayString = "${scheduler.interval.warehouse.return-goods:PT1M}")
    public void returnGoods() {
        List<Order> expiredAndCanceledOrders = orderRepository.findAllExpiredOrCanceled();
        for (Order order : expiredAndCanceledOrders) {
            if (order.getStatus() == OrderStatus.CREATED) {
                order.setStatus(OrderStatus.EXPIRED);
            }
            List<Reservation> reservations = reservationRepository.findAllByIdIn(order.getReservationIds());
            Map<Long, Supply> supplyMap = supplyRepository
                    .selectForUpdate(reservations.stream()
                            .map(Reservation::getSupplyId)
                            .collect(Collectors.toList()))
                    .stream()
                    .collect(Collectors.toMap(Supply::getId, Function.identity()));
            for (Reservation reservation : reservations) {
                Supply supply = supplyMap.get(reservation.getSupplyId());
                supply.setCount(supply.getCount() + reservation.getCount());
            }
            order.setReservationIds(null);
            orderRepository.save(order);
            supplyRepository.saveAll(supplyMap.values());
            reservationRepository.deleteAll(reservations);
        }
    }
}
