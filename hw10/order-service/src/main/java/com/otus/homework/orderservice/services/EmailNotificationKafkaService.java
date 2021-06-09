package com.otus.homework.orderservice.services;

import com.otus.homework.orderservice.dto.kafka.EmailNotificationTaskDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class EmailNotificationKafkaService {
    private final String topic;
    private final KafkaTemplate<String, EmailNotificationTaskDto> kafkaTemplate;
    private final int sendTimeout;

    public EmailNotificationKafkaService(@Value("${kafka.topic.email.task}") String topic,
                                         @Value("${kafka.producer.send.timeout.sec:5}") int sendTimeout,
                                         KafkaTemplate<String, EmailNotificationTaskDto> kafkaTemplate) {
        this.topic = topic;
        this.kafkaTemplate = kafkaTemplate;
        this.sendTimeout = sendTimeout;
    }

    public void sendToKafka(EmailNotificationTaskDto taskDto) {
        try {
            kafkaTemplate.send(topic, taskDto).get(sendTimeout, TimeUnit.SECONDS);
        } catch (Exception e) {
            log.error("Ошибка обработки таска", e);
        }
    }
}
