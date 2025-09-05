package com.sporty.test.service.impl;

import com.sporty.test.domain.Jackpot;
import com.sporty.test.dtos.BetDto;
import com.sporty.test.repository.JackpotRepository;
import com.sporty.test.service.IJackpotService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class JackpotService implements IJackpotService {

    private final JackpotRepository jackpotRepository;

    @Override
    public Optional<Jackpot> getJackpot(BetDto betDto) {
        return jackpotRepository.findById(betDto.getJackpotId())
                .filter(jackpot -> Objects.nonNull(jackpot.getConfiguration()));
    }
}
