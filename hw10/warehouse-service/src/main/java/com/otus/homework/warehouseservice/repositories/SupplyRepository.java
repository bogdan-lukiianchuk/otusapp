package com.otus.homework.warehouseservice.repositories;

import com.otus.homework.warehouseservice.entities.Supply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SupplyRepository extends JpaRepository<Supply, Long> {
    List<Supply> findAllByIdIn(List<Long> ids);

    @Query(value = "select * from supply where supply.supply_id in (:ids) for update", nativeQuery = true)
    List<Supply> selectForUpdate(@Param("ids") List<Long> ids);
}
