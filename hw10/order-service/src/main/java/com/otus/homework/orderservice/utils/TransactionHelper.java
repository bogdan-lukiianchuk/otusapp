package com.otus.homework.orderservice.utils;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Supplier;

@Service
public class TransactionHelper {
    @Transactional
    public <T> T doInTransaction(Supplier<T> supplier) {
        return supplier.get();
    }

    @Transactional
    public void doInTransaction(Runnable runnable) {
        runnable.run();
    }
}
