package com.otus.homework.deliveryservice.services;

import com.otus.homework.deliveryservice.dto.CancelReservationCourierDto;
import com.otus.homework.deliveryservice.dto.ReserveCourierDto;
import com.otus.homework.deliveryservice.enuns.Status;
import com.otus.homework.deliveryservice.exceptions.ApplicationException;
import com.otus.homework.deliveryservice.model.Courier;
import com.otus.homework.deliveryservice.model.DeliveryTime;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class DeliveryService {
    private static final List<Courier> COURIERS = List.of(
            new Courier("Вася", List.of(
                    new DeliveryTime(16, Status.FREE),
                    new DeliveryTime(17, Status.FREE),
                    new DeliveryTime(18, Status.FREE)
            )),
            new Courier("Коля", List.of(
                    new DeliveryTime(13, Status.FREE),
                    new DeliveryTime(14, Status.FREE),
                    new DeliveryTime(15, Status.FREE)
            )),
            new Courier("Дима", List.of(
                    new DeliveryTime(10, Status.FREE),
                    new DeliveryTime(11, Status.FREE),
                    new DeliveryTime(12, Status.FREE)
            ))
    );

    @Scheduled(cron = "${scheduler.interval.free-all-couriers:0 0 0 * *}")
    public void freeAllCouriers() {
        COURIERS.forEach(
                courier -> courier.getWorkTimes()
                        .forEach(it -> it.setStatus(Status.FREE))
        );
    }

    public String getFreeCourier(ReserveCourierDto reserveCourierDto) {
        log.debug("Курьеры {}", COURIERS);
        log.debug("Получен заказ {}", reserveCourierDto);
        int deliveryHour = reserveCourierDto.getDeliveryTime().getHour();
        Courier freeCourier = COURIERS
                .stream()
                .filter(it -> {
                    DeliveryTime time = it.getWorkTimes()
                            .stream()
                            .filter(t -> t.getHour().equals(deliveryHour) && t.getStatus() == Status.FREE)
                            .findFirst()
                            .orElse(null);
                    if (time == null) {
                        return false;
                    }
                    time.setStatus(Status.BUSY);
                    time.setOrderId(reserveCourierDto.getOrderId());
                    return true;
                })
                .findFirst()
                .orElse(null);
        if (freeCourier == null) {
            return null;
        }
        log.debug("Курьеры {}", COURIERS);
        return freeCourier.getName();
    }

    public void cancelDelivery(CancelReservationCourierDto cancelReservationCourierDto) {
        Courier courier = COURIERS.stream()
                .filter(c -> c.getName().equals(cancelReservationCourierDto.getCourierName()))
                .findFirst()
                .orElse(null);
        if (courier == null) {
            throw new ApplicationException("Курьер не найден");
        }
        courier.getWorkTimes()
                .stream()
                .filter(it -> it.getOrderId() != null
                        && it.getOrderId().equals(cancelReservationCourierDto.getOrderId()))
                .findFirst()
                .ifPresent(t -> t.setStatus(Status.FREE));
    }
}
