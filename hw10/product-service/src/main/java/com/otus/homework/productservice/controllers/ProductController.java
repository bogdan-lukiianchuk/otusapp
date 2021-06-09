package com.otus.homework.productservice.controllers;

import com.otus.homework.productservice.dto.CreateProductDto;
import com.otus.homework.productservice.dto.ProductAvailability;
import com.otus.homework.productservice.dto.ProductDto;
import com.otus.homework.productservice.dto.ProductPrice;
import com.otus.homework.productservice.services.ProductDtoMapper;
import com.otus.homework.productservice.services.ProductService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Api(tags = {"Products"})
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;
    private final ProductDtoMapper productDtoMapper;

    @GetMapping("/{id}")
    public ProductDto getById(@PathVariable("id") UUID id) {
        return productDtoMapper.toDto(productService.getById(id));
    }

    @GetMapping("/")
    public Page<ProductDto> getAll(@RequestParam(value = "sortBy", defaultValue = "name") String sortBy,
                                   @RequestParam(value = "category", required = false) String category,
                                   @RequestParam(value = "brand", required = false) String brand,
                                   @RequestParam(value = "priceMin", required = false) Integer priceMin,
                                   @RequestParam(value = "priceMax", required = false) Integer priceMax,
                                   @RequestParam(value = "limit", defaultValue = "10") Integer limit,
                                   @RequestParam(value = "page", defaultValue = "0") Integer page) {
        return productService.getAllByFiltration(sortBy, page, limit, category, brand, priceMin, priceMax)
                .map(productDtoMapper::toDto);
    }

    @GetMapping("/list")
    public List<ProductDto> getAllList() {
        return productService
                .getAll()
                .stream()
                .map(productDtoMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/availability")
    public List<ProductAvailability> getAvailability(@RequestParam(value = "ids") List<UUID> ids) {
        return productService.getAvailability(ids);
    }

    @GetMapping("/price")
    public List<ProductPrice> getPrices(@RequestParam(value = "ids") List<UUID> ids) {
        return productService.getPrices(ids);
    }

    @PostMapping("/add")
    public ProductDto create(@RequestBody CreateProductDto product) {
        return productDtoMapper.toDto(productService.create(product));
    }
}
