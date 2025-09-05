package com.sporty.test.kafka;

import com.sporty.test.dtos.BetDto;
import com.sporty.test.service.IJackpotContributionService;
import com.sporty.test.service.IJackpotService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaConsumer {

    private final IJackpotService jackpotService;
    private final IJackpotContributionService jackpotContributionService;

    @KafkaListener(
            id = "jackpot-listener",
            topics = "${kafka.topic.name}"
    )
    public void consumeRecord(final ConsumerRecord<String, BetDto> record) {
        final BetDto betDto = record.value();
        log.info("[1] Received record on {}-{}: {}={}", record.partition(), record.offset(), record.key(), record.value());
        log.info("Bet with id {}", betDto.getBetId());
        jackpotService.getJackpot(betDto)
                .ifPresent(jackpot -> jackpotContributionService.calculateContribution(jackpot, betDto));
    }


}
