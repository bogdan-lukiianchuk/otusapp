package com.otus.homework.productservice.repositories;

import com.otus.homework.productservice.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {

    @Query("select p from Product p" +
            " where (:category is null or lower(p.category) like concat('%', lower(cast(:category as text)),'%')) " +
            " and (:brand is null or lower(p.brand) like concat('%', lower(cast(:brand as text)),'%')) " +
            " and p.price between :priceMin and :priceMax")
    Page<Product> findAllByFilters(@Param("category") String category,
                                   @Param("brand") String brand,
                                   @Param("priceMin") int priceMin,
                                   @Param("priceMax") int priceMax,
                                   Pageable pageable);

    List<Product> findAllByIdIn(List<UUID> ids);
}
