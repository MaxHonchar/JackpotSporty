package com.sporty.test.strategy.factory;

import com.sporty.test.domain.enums.ContributorType;
import com.sporty.test.strategy.RewardStrategy;
import com.sporty.test.strategy.impl.FixedRewardStrategy;
import com.sporty.test.strategy.impl.VariableRewardStrategy;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class RewardStrategyFactory {

    private final Map<ContributorType, RewardStrategy> strategies = new EnumMap<>(ContributorType.class);

    private final FixedRewardStrategy fixedRewardStrategy;
    private final VariableRewardStrategy variableRewardStrategy;

    @PostConstruct
    public void init() {
        strategies.put(ContributorType.FIXED, fixedRewardStrategy);
        strategies.put(ContributorType.VARIABLE, variableRewardStrategy);
    }

    public RewardStrategy getStrategy(ContributorType type) {
        return Optional.ofNullable(strategies.get(type))
                .orElseThrow(() -> new IllegalArgumentException("Unsupported strategy"));
    }

}
