package com.sporty.test.service;

import com.sporty.test.domain.Jackpot;
import com.sporty.test.dtos.BetDto;

import java.util.Optional;

public interface IJackpotService {
    Optional<Jackpot> getJackpot(final BetDto betDto);
}
