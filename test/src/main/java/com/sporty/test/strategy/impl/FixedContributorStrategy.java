package com.sporty.test.strategy.impl;

import com.sporty.test.domain.Jackpot;
import com.sporty.test.domain.JackpotConfiguration;
import com.sporty.test.domain.enums.ContributorType;
import com.sporty.test.strategy.ContributorStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.sporty.test.utils.JackpotUtils.ZERO;
import static com.sporty.test.utils.JackpotUtils.getPercentage;

@Slf4j
@Component
public class FixedContributorStrategy implements ContributorStrategy {

    @Override
    public double applyStrategy(Jackpot jackpot, double betAmount) {
        log.info("Apply contributor strategy {}", ContributorType.FIXED);
        JackpotConfiguration configuration = jackpot.getConfiguration();
        if(ContributorType.FIXED.equals(configuration.getContributorType())
                && isValid(configuration, betAmount)) {
            return betAmount * getPercentage(configuration.getFixedPercentage());
        }
        return ZERO;
    }

    private boolean isValid(JackpotConfiguration configuration, double betAmount) {
        return betAmount > ZERO && configuration.getInitialPool() > ZERO
                && configuration.getFixedPercentage() > ZERO;
    }

}
