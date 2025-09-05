package com.sporty.test.service.impl;

import com.sporty.test.domain.Jackpot;
import com.sporty.test.domain.JackpotConfiguration;
import com.sporty.test.domain.JackpotContribution;
import com.sporty.test.dtos.BetDto;
import com.sporty.test.repository.JackpotContributionRepository;
import com.sporty.test.repository.JackpotRepository;
import com.sporty.test.service.IJackpotContributionService;
import com.sporty.test.strategy.ContributorStrategy;
import com.sporty.test.strategy.factory.ContributorStrategyFactory;
import com.sporty.test.utils.JackpotUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class JackpotContributionService implements IJackpotContributionService {

    private final JackpotRepository jackpotRepository;
    private final JackpotContributionRepository jackpotContributionRepository;
    private final ContributorStrategyFactory contributorStrategyFactory;

    @Override
    public void calculateContribution(Jackpot jackpot, BetDto betDto) {
        final JackpotContribution jackpotContribution = getJackpotContribution(jackpot, betDto);
        final JackpotConfiguration configuration = jackpot.getConfiguration();
        final ContributorStrategy contributorStrategy = contributorStrategyFactory.getStrategy(configuration.getContributorType());

        double contribution = contributorStrategy.applyStrategy(jackpot, betDto.getBetAmount());
        double totalContribution = jackpotContribution.getContributionAmount() + contribution;
        double totalStakeAmount = jackpotContribution.getStakeAmount() + betDto.getBetAmount();
        double totalAmount = Objects.nonNull(jackpot.getTotalAmount()) && jackpot.getTotalAmount() > JackpotUtils.ZERO
                ? jackpot.getTotalAmount() + contribution
                : configuration.getInitialPool() + contribution;

        jackpotContribution.setStakeAmount(totalStakeAmount);
        jackpotContribution.setContributionAmount(totalContribution);
        jackpotContribution.setCurrentJackpotAmount(totalAmount);
        jackpotContributionRepository.save(jackpotContribution);

        jackpot.setTotalAmount(totalAmount);
        jackpotRepository.save(jackpot);
    }

    @Override
    public Optional<JackpotContribution> getJackpotContribution(BetDto betDto) {
        return jackpotContributionRepository.findByJackpotIdAndBetIdAndUserId(betDto.getJackpotId(),
                betDto.getBetId(), betDto.getUserId());
    }

    @Override
    public void removeJackpotContributions(Jackpot jackpot) {
        List<JackpotContribution> contributions = jackpotContributionRepository.findByJackpot(jackpot);
        if(!contributions.isEmpty()) {
            jackpotContributionRepository.deleteAll(contributions);
        }
    }

    private JackpotContribution getJackpotContribution(Jackpot jackpot, BetDto betDto) {
        return getJackpotContribution(betDto).orElseGet(() -> {
            JackpotContribution jackpotContribution = new JackpotContribution();
            jackpotContribution.setBetId(betDto.getBetId());
            jackpotContribution.setUserId(betDto.getUserId());
            jackpotContribution.setJackpot(jackpot);
            jackpotContribution.setContributionAmount(JackpotUtils.ZERO);
            jackpotContribution.setStakeAmount(JackpotUtils.ZERO);
            return jackpotContribution;
        });
    }
}
