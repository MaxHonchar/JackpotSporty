package com.sporty.test.strategy;

import com.sporty.test.domain.Jackpot;

public interface ContributorStrategy {

    double applyStrategy(final Jackpot jackpot, double betAmount);

}
