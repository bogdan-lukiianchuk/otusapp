package com.otus.homework.billingservice.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "operation_code")
public class OperationCode {
    @Id
    @Column(name = "operation_code_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "operation")
    private String operation;
}
