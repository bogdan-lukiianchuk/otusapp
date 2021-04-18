package com.otus.homework.orderservice.repositories;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class OrderSequenceRepository {

    private final JdbcTemplate jdbcTemplate;

    public OrderSequenceRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long nextVal() {
        return jdbcTemplate.query("select nextval('order_sequence')", rs -> {
            if (rs.next()) {
                return rs.getLong("nextval");
            } else {
                throw new EmptyResultDataAccessException("Ошибка при получении значения сиквенса", 1);
            }
        });
    }
}
