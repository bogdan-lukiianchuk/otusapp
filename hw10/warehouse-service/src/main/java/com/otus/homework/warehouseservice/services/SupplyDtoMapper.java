package com.otus.homework.warehouseservice.services;

import com.otus.homework.warehouseservice.dto.SupplyDto;
import com.otus.homework.warehouseservice.entities.Supply;
import org.springframework.stereotype.Service;

@Service
public class SupplyDtoMapper {

    public Supply map(SupplyDto dto){
        Supply supply = new Supply();
        supply.setCount(dto.getCount());
        supply.setId(dto.getProductId());
        return supply;
    }

    public SupplyDto map(Supply entity) {
        return new SupplyDto(entity.getId(), entity.getCount());
    }
}
