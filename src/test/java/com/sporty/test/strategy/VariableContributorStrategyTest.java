package com.sporty.test.strategy;

import com.sporty.test.domain.Jackpot;
import com.sporty.test.domain.JackpotConfiguration;
import com.sporty.test.domain.enums.ContributorType;
import com.sporty.test.strategy.impl.VariableContributorStrategy;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static com.sporty.test.utils.JackpotUtils.*;

public class VariableContributorStrategyTest {
    private final VariableContributorStrategy strategy = new VariableContributorStrategy();

    private Jackpot buildJackpot(
            ContributorType type,
            double initialPool,
            Double totalAmount,
            double threshold,
            double minPercentage,
            double maxPercentage
    ) {
        JackpotConfiguration config = new JackpotConfiguration();
        config.setContributorType(type);
        config.setInitialPool(initialPool);
        config.setThresholdAmount(threshold);
        config.setMinPercentage(minPercentage);
        config.setMaxPercentage(maxPercentage);

        Jackpot jackpot = new Jackpot();
        jackpot.setConfiguration(config);
        jackpot.setTotalAmount(totalAmount);
        return jackpot;
    }

    @Test
    void applyStrategy_validCase_shouldReturnCalculatedContribution() {
        double betAmount = 100.0;
        Jackpot jackpot = buildJackpot(
                ContributorType.VARIABLE,
                500.0,
                1000.0,
                2000.0,
                5.0,
                20.0
        );

        double result = strategy.applyStrategy(jackpot, betAmount);

        double totalAmount = 1000.0;
        double contributeAmount = totalAmount / 2000.0;
        double expectedContribute = getPercentage(20.0) - contributeAmount * getPercentage(5.0);
        double expected = betAmount * expectedContribute;

        assertEquals(expected, result);
    }

    @Test
    void applyStrategy_totalAmountNull_shouldFallbackToInitialPool() {
        double betAmount = 50.0;
        Jackpot jackpot = buildJackpot(
                ContributorType.VARIABLE,
                400.0,
                null,
                800.0,
                2.0,
                10.0
        );

        double result = strategy.applyStrategy(jackpot, betAmount);

        double totalAmount = 400.0;
        double contributeAmount = totalAmount / 800.0;
        double expectedContribute = getPercentage(10.0) - contributeAmount * getPercentage(2.0);
        double expected = betAmount * expectedContribute;

        assertEquals(expected, result);
    }

    @Test
    void applyStrategy_totalAmountZero_shouldFallbackToInitialPool() {
        double betAmount = 50.0;
        Jackpot jackpot = buildJackpot(
                ContributorType.VARIABLE,
                300.0,
                0.0,
                600.0,
                1.0,
                5.0
        );

        double result = strategy.applyStrategy(jackpot, betAmount);

        double totalAmount = 300.0;
        double contributeAmount = totalAmount / 600.0;
        double expectedContribute = getPercentage(5.0) - contributeAmount * getPercentage(1.0);
        double expected = betAmount * expectedContribute;

        assertEquals(expected, result);
    }

    @Test
    void applyStrategy_wrongContributorType_shouldReturnZero() {
        Jackpot jackpot = buildJackpot(
                ContributorType.FIXED,
                500.0,
                1000.0,
                2000.0,
                5.0,
                20.0
        );

        double result = strategy.applyStrategy(jackpot, 100.0);

        assertEquals(ZERO, result);
    }

    @Test
    void applyStrategy_initialPoolZero_shouldReturnZero() {
        Jackpot jackpot = buildJackpot(
                ContributorType.VARIABLE,
                0.0,
                1000.0,
                2000.0,
                5.0,
                20.0
        );

        double result = strategy.applyStrategy(jackpot, 100.0);

        assertEquals(ZERO, result);
    }

    @Test
    void applyStrategy_betAmountZero_shouldReturnZero() {
        Jackpot jackpot = buildJackpot(
                ContributorType.VARIABLE,
                500.0,
                1000.0,
                2000.0,
                5.0,
                20.0
        );

        double result = strategy.applyStrategy(jackpot, 0.0);

        assertEquals(ZERO, result);
    }

    @Test
    void applyStrategy_minPercentageZero_shouldReturnZero() {
        Jackpot jackpot = buildJackpot(
                ContributorType.VARIABLE,
                500.0,
                1000.0,
                2000.0,
                0.0,
                20.0
        );

        double result = strategy.applyStrategy(jackpot, 100.0);

        assertEquals(ZERO, result);
    }

    @Test
    void applyStrategy_maxPercentageZero_shouldReturnZero() {
        Jackpot jackpot = buildJackpot(
                ContributorType.VARIABLE,
                500.0,
                1000.0,
                2000.0,
                5.0,
                0.0
        );

        double result = strategy.applyStrategy(jackpot, 100.0);

        assertEquals(ZERO, result);
    }

    @Test
    void applyStrategy_minPercentageGreaterOrEqualToMax_shouldReturnZero() {
        Jackpot jackpot = buildJackpot(
                ContributorType.VARIABLE,
                500.0,
                1000.0,
                2000.0,
                20.0,
                20.0
        );

        double result = strategy.applyStrategy(jackpot, 100.0);

        assertEquals(ZERO, result);
    }
}
