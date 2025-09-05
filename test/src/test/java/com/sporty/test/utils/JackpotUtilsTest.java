package com.sporty.test.utils;

import com.sporty.test.domain.Jackpot;
import com.sporty.test.domain.JackpotConfiguration;
import com.sporty.test.domain.enums.ContributorType;

public class JackpotUtilsTest {

    public static Jackpot buildJackpot(ContributorType type, double initialPool, double fixedPercentage) {
        JackpotConfiguration config = new JackpotConfiguration();
        config.setContributorType(type);
        config.setInitialPool(initialPool);
        config.setFixedPercentage(fixedPercentage);

        Jackpot jackpot = new Jackpot();
        jackpot.setConfiguration(config);
        return jackpot;
    }

}
