package com.otus.homework.productservice.entities;

import lombok.Data;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Data
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue
    @Column(name = "product_id")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "price")
    private int price;
    @Column(name = "availability_date")
    private Instant availabilityDate;
    @Column(name = "category")
    private String category;
    @Column(name = "brand")
    private String brand;
}
