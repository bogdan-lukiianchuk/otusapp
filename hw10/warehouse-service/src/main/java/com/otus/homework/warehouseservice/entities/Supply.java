package com.otus.homework.warehouseservice.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Table(name = "supply")
public class Supply {
    @Id
    @Column(name = "supply_id")
    private Long id;
    @Column(name = "count")
    private int count;
}
