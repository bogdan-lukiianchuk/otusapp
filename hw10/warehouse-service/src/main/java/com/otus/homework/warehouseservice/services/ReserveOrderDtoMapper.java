package com.otus.homework.warehouseservice.services;

import com.otus.homework.warehouseservice.dto.ReserveOrderDto;
import com.otus.homework.warehouseservice.dto.SupplyDto;
import com.otus.homework.warehouseservice.entities.Order;
import com.otus.homework.warehouseservice.repositories.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ReserveOrderDtoMapper {
    private final ReservationRepository reservationRepository;

    public ReserveOrderDto map(Order entity) {
        List<SupplyDto> supplies = List.of();
        if (!CollectionUtils.isEmpty(entity.getReservationIds())) {
            supplies = reservationRepository.findAllByIdIn(entity.getReservationIds())
                    .stream()
                    .map(it -> new SupplyDto(it.getSupplyId(), it.getCount()))
                    .collect(Collectors.toList());
        }
        return new ReserveOrderDto(
                entity.getId(),
                entity.getStatus(),
                supplies,
                entity.getCreatedTime(),
                entity.getReservationEndTime()
        );
    }
}
