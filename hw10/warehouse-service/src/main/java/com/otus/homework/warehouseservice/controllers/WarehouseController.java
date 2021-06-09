package com.otus.homework.warehouseservice.controllers;

import com.otus.homework.warehouseservice.dto.CreatedReplenishmentDto;
import com.otus.homework.warehouseservice.dto.ReplenishmentDto;
import com.otus.homework.warehouseservice.dto.ReserveOrderDto;
import com.otus.homework.warehouseservice.entities.Replenishment;
import com.otus.homework.warehouseservice.services.ReserveOrderDtoMapper;
import com.otus.homework.warehouseservice.services.WarehouseService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"Warehouse"})
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/warehouse")
public class WarehouseController {
    private final WarehouseService warehouseService;
    private final ReserveOrderDtoMapper reserveOrderDtoMapper;

    @GetMapping("/products/{id}")
    public Integer getAmountById(@PathVariable("id") Long id) {
        return warehouseService.getAmountById(id);
    }

    @PostMapping("/replenishment")
    public CreatedReplenishmentDto replenishment(@RequestBody ReplenishmentDto replenishmentDto) {
        Replenishment replenishment = warehouseService.replenishment(replenishmentDto);
        return new CreatedReplenishmentDto(
                replenishment.getId(),
                replenishment.getReplenishmentCode(),
                replenishment.getSupplies());
    }

    @GetMapping("/reservation/{orderId}")
    public ReserveOrderDto getReservation(@PathVariable("orderId") Long orderId) {
        return reserveOrderDtoMapper
                .map(warehouseService.getOrderById(orderId));
    }
}
