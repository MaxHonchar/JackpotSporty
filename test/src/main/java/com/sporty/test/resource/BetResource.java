package com.sporty.test.resource;

import com.sporty.test.dtos.BetDto;
import com.sporty.test.kafka.KafkaPublisher;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/bet")
public class BetResource {

    private final KafkaPublisher kafkaPublisher;

    @PostMapping
    public ResponseEntity<Void> sendBet(@Valid @RequestBody BetDto betDto) {
        log.info("Sending Bet: {}", betDto.getBetId());
        kafkaPublisher.publish(betDto);
        log.info("Published Bet: {}", betDto.getBetId());
        return ResponseEntity.accepted().build();
    }

}
