package com.sporty.test.kafka;

import com.sporty.test.dtos.BetDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaPublisher {

    @Value("${kafka.topic.name}")
    private String topic;

    private final KafkaTemplate<String, BetDto> kafkaTemplate;

    public void publish(final BetDto betDto) {
        final UUID uuid = UUID.randomUUID();
        CompletableFuture<SendResult<String, BetDto>> future = kafkaTemplate.send(topic, uuid.toString(), betDto);
        future.thenAccept(result -> {
                    final ProducerRecord<String, BetDto> producerRecord = result.getProducerRecord();
                    final RecordMetadata recordMetadata = result.getRecordMetadata();
                    log.info("Metadata Record:, offset = {}", recordMetadata.offset());
                    log.info("Producer Record: key = {}", producerRecord.key());
                })
                .exceptionally(throwable -> {
                    log.error("Error publishing bet", throwable);
                    return null;
                });
    }

}
