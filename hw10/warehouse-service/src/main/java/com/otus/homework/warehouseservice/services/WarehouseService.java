package com.otus.homework.warehouseservice.services;

import com.otus.homework.warehouseservice.dto.ReplenishmentDto;
import com.otus.homework.warehouseservice.dto.SupplyDto;
import com.otus.homework.warehouseservice.entities.Order;
import com.otus.homework.warehouseservice.entities.Replenishment;
import com.otus.homework.warehouseservice.entities.Supply;
import com.otus.homework.warehouseservice.repositories.OrderRepository;
import com.otus.homework.warehouseservice.repositories.ReplenishmentRepository;
import com.otus.homework.warehouseservice.repositories.SupplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class WarehouseService {
    private final ReplenishmentRepository replenishmentRepository;
    private final SupplyRepository supplyRepository;
    private final SupplyDtoMapper supplyDtoMapper;
    private final OrderRepository orderRepository;

    public Integer getAmountById(Long id) {
        return supplyRepository.findById(id).map(Supply::getCount).orElse(0);
    }

    @Transactional(readOnly = true)
    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElseThrow();
    }

    @Transactional
    public Replenishment replenishment(ReplenishmentDto replenishmentDto) {
        Replenishment replenishment = new Replenishment();
        replenishment.setReplenishmentCode(replenishmentDto.getReplenishmentCode());
        replenishment.setSupplies(replenishmentDto.getSupplies());
        Map<Long, Supply> warehouseMap = new HashMap<>();
        supplyRepository.findAllByIdIn(
                replenishmentDto
                        .getSupplies()
                        .stream()
                        .map(SupplyDto::getProductId)
                        .collect(Collectors.toList()))
                .forEach(v -> warehouseMap.put(v.getId(), v));
        replenishment.getSupplies()
                .forEach(supplyDto ->
                        warehouseMap.merge(supplyDto.getProductId(), supplyDtoMapper.map(supplyDto),
                                (oldVal, newVal) -> {
                                    oldVal.setCount(oldVal.getCount() + newVal.getCount());
                                    return oldVal;
                                }));
        supplyRepository.saveAll(warehouseMap.values());
        return replenishmentRepository.save(replenishment);
    }
}
