package com.sporty.test.strategy;

import com.sporty.test.domain.Jackpot;
import com.sporty.test.domain.enums.ContributorType;
import com.sporty.test.strategy.impl.FixedContributorStrategy;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static com.sporty.test.utils.JackpotUtils.*;
import static com.sporty.test.utils.JackpotUtilsTest.buildJackpot;

public class FixedContributorStrategyTest {

        private final FixedContributorStrategy strategy =  new FixedContributorStrategy();

        @Test
        void applyStrategy_validCase_shouldReturnCalculatedContribution() {
            double betAmount = 100.0;
            double fixedPercentage = 10.0;
            Jackpot jackpot = buildJackpot(ContributorType.FIXED, 500.0, fixedPercentage);

            double result = strategy.applyStrategy(jackpot, betAmount);

            double expected = betAmount * getPercentage(fixedPercentage);
            assertEquals(expected, result);
        }

        @Test
        void applyStrategy_invalidType_shouldReturnZero() {
            Jackpot jackpot = buildJackpot(ContributorType.VARIABLE, 500.0, 10.0);

            double result = strategy.applyStrategy(jackpot, 100.0);

            assertEquals(ZERO, result);
        }

        @Test
        void applyStrategy_initialPoolZero_shouldReturnZero() {
            Jackpot jackpot = buildJackpot(ContributorType.FIXED, 0.0, 10.0);

            double result = strategy.applyStrategy(jackpot, 100.0);

            assertEquals(ZERO, result);
        }

        @Test
        void applyStrategy_initialPoolNegative_shouldReturnZero() {
            Jackpot jackpot = buildJackpot(ContributorType.FIXED, -100.0, 10.0);

            double result = strategy.applyStrategy(jackpot, 100.0);

            assertEquals(ZERO, result);
        }

        @Test
        void applyStrategy_betAmountZero_shouldReturnZero() {
            Jackpot jackpot = buildJackpot(ContributorType.FIXED, 500.0, 10.0);

            double result = strategy.applyStrategy(jackpot, 0.0);

            assertEquals(ZERO, result);
        }

        @Test
        void applyStrategy_betAmountNegative_shouldReturnZero() {
            Jackpot jackpot = buildJackpot(ContributorType.FIXED, 500.0, 10.0);

            double result = strategy.applyStrategy(jackpot, -50.0);

            assertEquals(ZERO, result);
        }

        @Test
        void applyStrategy_fixedPercentageZero_shouldReturnZero() {
            Jackpot jackpot = buildJackpot(ContributorType.FIXED, 500.0, 0.0);

            double result = strategy.applyStrategy(jackpot, 100.0);

            assertEquals(ZERO, result);
        }

        @Test
        void applyStrategy_fixedPercentageNegative_shouldReturnZero() {
            Jackpot jackpot = buildJackpot(ContributorType.FIXED, 500.0, -5.0);

            double result = strategy.applyStrategy(jackpot, 100.0);

            assertEquals(ZERO, result);
        }

}
