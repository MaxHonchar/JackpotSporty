package com.sporty.test.strategy.impl;

import com.sporty.test.domain.Jackpot;
import com.sporty.test.domain.JackpotRewardConfiguration;
import com.sporty.test.domain.enums.ContributorType;
import com.sporty.test.strategy.RewardStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadLocalRandom;

import static com.sporty.test.utils.JackpotUtils.*;

@Slf4j
@Component
public class FixedRewardStrategy implements RewardStrategy {

    @Override
    public boolean isWin(Jackpot jackpot) {
        log.info("Apply reward strategy {}", ContributorType.FIXED);
        JackpotRewardConfiguration configuration = jackpot.getRewardConfiguration();
        return ContributorType.FIXED.equals(configuration.getContributorType())
                && ThreadLocalRandom.current().nextDouble(ZERO, ONE) < getPercentage(configuration.getFixedChanceReward());
    }
}
