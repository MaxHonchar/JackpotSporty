package com.sporty.test.service;

import com.sporty.test.domain.Jackpot;
import com.sporty.test.dtos.BetDto;
import com.sporty.test.dtos.RewardDto;

import java.util.Optional;

public interface IRewardService {

    Optional<Jackpot> getJackpot(BetDto betDto);
    RewardDto updateJackpot(Jackpot jackpot, BetDto betDto);

}
