package com.otus.homework.orderservice;

import com.otus.homework.orderservice.entities.OrderStage;
import com.otus.homework.orderservice.enums.OrderStages;
import com.otus.homework.orderservice.repositories.OrderStageRepository;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

//@SpringBootTest
class OrderStageRepositoryTest {
  //  @Autowired
    private OrderStageRepository orderStageRepository;

    @Test
    void getAllByStage() {
        List<OrderStage> stage = orderStageRepository
                .getAllByStage(OrderStages.DELIVERY_ERROR.name());
        System.out.println(stage);
    }

    @Test
    void save() {
        orderStageRepository.save(new OrderStage(4L, OrderStages.DELIVERY_ERROR, LocalDateTime.now()));
    }


}