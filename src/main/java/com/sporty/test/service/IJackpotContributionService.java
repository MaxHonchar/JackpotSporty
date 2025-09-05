package com.sporty.test.service;

import com.sporty.test.domain.Jackpot;
import com.sporty.test.domain.JackpotContribution;
import com.sporty.test.dtos.BetDto;

import java.util.List;
import java.util.Optional;

public interface IJackpotContributionService {
    void calculateContribution(final Jackpot jackpot, final BetDto betDto);
    Optional<JackpotContribution> getJackpotContribution(BetDto betDto);
    void removeJackpotContributions(Jackpot jackpot);
}
