package com.sporty.test.strategy.impl;

import com.sporty.test.domain.Jackpot;
import com.sporty.test.domain.JackpotConfiguration;
import com.sporty.test.domain.enums.ContributorType;
import com.sporty.test.strategy.RewardStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadLocalRandom;

import static com.sporty.test.utils.JackpotUtils.*;

@Slf4j
@Component
public class VariableRewardStrategy implements RewardStrategy {

    @Override
    public boolean isWin(Jackpot jackpot) {
        log.info("Apply reward strategy {}", ContributorType.VARIABLE);
        JackpotConfiguration jackpotConfiguration = jackpot.getConfiguration();
        if(ContributorType.VARIABLE.equals(jackpotConfiguration.getContributorType())) {
            if(jackpot.getTotalAmount() >= jackpotConfiguration.getThresholdAmount()) return true;
            double minPercentage = getPercentage(jackpotConfiguration.getMinPercentage());
            double value = jackpot.getTotalAmount() / jackpotConfiguration.getThresholdAmount();
            double chance = minPercentage + value * (ONE - minPercentage);
            return ThreadLocalRandom.current().nextDouble(ZERO, ONE) < chance;
        }
        return false;
    }
}
