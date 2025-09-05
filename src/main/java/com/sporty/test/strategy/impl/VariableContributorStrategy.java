package com.sporty.test.strategy.impl;

import com.sporty.test.domain.Jackpot;
import com.sporty.test.domain.JackpotConfiguration;
import com.sporty.test.domain.enums.ContributorType;
import com.sporty.test.strategy.ContributorStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static com.sporty.test.utils.JackpotUtils.ZERO;
import static com.sporty.test.utils.JackpotUtils.getPercentage;

@Slf4j
@Component
public class VariableContributorStrategy implements ContributorStrategy {

    @Override
    public double applyStrategy(Jackpot jackpot, double betAmount) {
        log.info("Apply contributor strategy {}", ContributorType.VARIABLE);
        JackpotConfiguration configuration = jackpot.getConfiguration();
        if (ContributorType.VARIABLE.equals(configuration.getContributorType())
                && isValid(configuration, betAmount)) {
            Double totalAmount = getTotalAmount(jackpot);
            Double contributeAmount = totalAmount/configuration.getThresholdAmount();
            Double contribute = getPercentage(configuration.getMaxPercentage()) - contributeAmount * getPercentage(configuration.getMinPercentage());
            return betAmount * contribute;
        }
        return ZERO;
    }

    private Double getTotalAmount(Jackpot jackpot) {
        return Objects.nonNull(jackpot.getTotalAmount()) && jackpot.getTotalAmount() > ZERO
                ? jackpot.getTotalAmount()
                : jackpot.getConfiguration().getInitialPool();
    }

    private boolean isValid(JackpotConfiguration configuration, double betAmount) {
        return betAmount > ZERO && configuration.getInitialPool() > ZERO
                && configuration.getMinPercentage() > ZERO
                && configuration.getMaxPercentage() > ZERO
                && isMinBeforeMax(configuration);
    }

    private boolean isMinBeforeMax(JackpotConfiguration configuration) {
        return configuration.getMinPercentage() < configuration.getMaxPercentage();
    }

}
