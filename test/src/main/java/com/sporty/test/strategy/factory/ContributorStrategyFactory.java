package com.sporty.test.strategy.factory;

import com.sporty.test.domain.enums.ContributorType;
import com.sporty.test.strategy.ContributorStrategy;
import com.sporty.test.strategy.impl.FixedContributorStrategy;
import com.sporty.test.strategy.impl.VariableContributorStrategy;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ContributorStrategyFactory {
    private final Map<ContributorType, ContributorStrategy> strategies = new EnumMap<>(ContributorType.class);

    private final FixedContributorStrategy fixedContributorStrategy;
    private final VariableContributorStrategy variableContributorStrategy;

    @PostConstruct
    public void init() {
        strategies.put(ContributorType.FIXED, fixedContributorStrategy);
        strategies.put(ContributorType.VARIABLE, variableContributorStrategy);
    }

    public ContributorStrategy getStrategy(ContributorType type) {
        return Optional.ofNullable(strategies.get(type))
                .orElseThrow(() -> new IllegalArgumentException("Unsupported strategy"));
    }

}
