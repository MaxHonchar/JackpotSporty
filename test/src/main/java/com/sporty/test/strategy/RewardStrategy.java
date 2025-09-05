package com.sporty.test.strategy;

import com.sporty.test.domain.Jackpot;

public interface RewardStrategy {
    boolean isWin(final Jackpot jackpot);
}
