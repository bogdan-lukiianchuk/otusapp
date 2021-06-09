package com.otus.homework.productservice.services;

import com.otus.homework.productservice.dto.CreateProductDto;
import com.otus.homework.productservice.dto.ProductAvailability;
import com.otus.homework.productservice.dto.ProductPrice;
import com.otus.homework.productservice.entities.Product;
import com.otus.homework.productservice.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProductService {
    private final ProductRepository productRepository;

    @Transactional(readOnly = true)
    public Product getById(UUID id) {
        return productRepository.findById(id).orElseThrow();
    }

    @Transactional(readOnly = true)
    public Page<Product> getAllByFiltration(String sortBy, Integer page, Integer size,
                                            String category, String brand, Integer priceMin, Integer priceMax) {
        int min = priceMin == null ? 0 : priceMin;
        int max = priceMax == null ? Integer.MAX_VALUE : priceMax;
        return productRepository.findAllByFilters(category, brand, min, max, PageRequest.of(page, size, Sort.by(sortBy)));
    }

    @Transactional(readOnly = true)
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<ProductAvailability> getAvailability(List<UUID> ids) {
        return productRepository.findAllByIdIn(ids).stream()
                .map(it -> new ProductAvailability(it.getId(), it.getAvailabilityDate()))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ProductPrice> getPrices(List<UUID> ids) {
        return productRepository.findAllByIdIn(ids).stream()
                .map(it -> new ProductPrice(it.getId(), it.getPrice()))
                .collect(Collectors.toList());
    }

    @Transactional
    public Product create(CreateProductDto productDto) {
        Product product = new Product();
        product.setBrand(productDto.getBrand());
        product.setCategory(productDto.getCategory());
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setAvailabilityDate(productDto.getAvailabilityDate());
        return productRepository.save(product);
    }
}
