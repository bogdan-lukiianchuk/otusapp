package com.otus.homework.productservice.services;

import com.otus.homework.productservice.dto.ProductDto;
import com.otus.homework.productservice.entities.Product;
import org.springframework.stereotype.Service;

@Service
public class ProductDtoMapper {

    public ProductDto toDto(Product entity) {
        return new ProductDto(entity.getId(),
                entity.getName(),
                entity.getCategory(),
                entity.getBrand(),
                entity.getPrice());
    }
}
