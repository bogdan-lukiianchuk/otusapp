package com.otus.homework.warehouseservice.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "reservation")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id")
    private Long id;
    @Column(name = "supply_id")
    private Long supplyId;
    @Column(name = "count")
    private Integer count;
}
