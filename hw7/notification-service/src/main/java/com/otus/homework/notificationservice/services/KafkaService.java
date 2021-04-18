package com.otus.homework.notificationservice.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.otus.homework.notificationservice.dto.EmailNotificationTaskDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Service
public class KafkaService {
    private final EmailNotificationService emailNotificationService;
    private final ObjectMapper objectMapper;

    @Transactional
    @KafkaListener(topics = "${kafka.topic.email.task}", autoStartup = "${kafka.enable:false}")
    public void onSplitStatus(ConsumerRecord<String, byte[]> consumerRecord, Acknowledgment acknowledgment) {
        try {
            EmailNotificationTaskDto notificationDto = objectMapper.readValue(consumerRecord.value(), EmailNotificationTaskDto.class);
            log.info("Получен задание на отправку email {}", notificationDto);
            emailNotificationService.sendEmail(notificationDto);
        } catch (IOException e) {
            log.error("ошибка обработки сообщения {}", new String(consumerRecord.value()), e);
        } finally {
            acknowledgment.acknowledge();
        }
    }
}
