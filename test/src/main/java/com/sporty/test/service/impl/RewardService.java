package com.sporty.test.service.impl;

import com.sporty.test.domain.Jackpot;
import com.sporty.test.domain.JackpotContribution;
import com.sporty.test.domain.JackpotReward;
import com.sporty.test.domain.JackpotRewardConfiguration;
import com.sporty.test.dtos.BetDto;
import com.sporty.test.dtos.RewardDto;
import com.sporty.test.repository.JackpotRepository;
import com.sporty.test.repository.JackpotRewardRepository;
import com.sporty.test.service.IJackpotContributionService;
import com.sporty.test.service.IRewardService;
import com.sporty.test.strategy.RewardStrategy;
import com.sporty.test.strategy.factory.RewardStrategyFactory;
import com.sporty.test.utils.JackpotUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class RewardService implements IRewardService {

    private final JackpotRepository jackpotRepository;
    private final JackpotRewardRepository jackpotRewardRepository;
    private final IJackpotContributionService jackpotContributionService;
    private final RewardStrategyFactory rewardStrategyFactory;

    @Override
    public Optional<Jackpot> getJackpot(BetDto betDto) {
        return jackpotContributionService.getJackpotContribution(betDto)
                .map(JackpotContribution::getJackpot)
                .filter(jackpot -> Objects.nonNull(jackpot.getRewardConfiguration()))
                .filter(this::isWin);
    }

    @Transactional
    @Override
    public RewardDto updateJackpot(Jackpot jackpot, BetDto betDto) {
        JackpotReward jackpotReward = saveReward(jackpot, betDto);
        jackpot.setTotalAmount(JackpotUtils.ZERO);
        jackpotContributionService.removeJackpotContributions(jackpot);
        jackpotRepository.save(jackpot);
        return convertToDto(jackpotReward);
    }

    private RewardDto convertToDto(JackpotReward jackpotReward) {
        RewardDto rewardDto = new RewardDto();
        rewardDto.setRewardAmount(jackpotReward.getJackpotRewardAmount());
        rewardDto.setUserId(jackpotReward.getUserId());
        rewardDto.setJackpotId(jackpotReward.getJackpot().getId());
        return rewardDto;
    }

    private JackpotReward saveReward(Jackpot jackpot, BetDto betDto) {
        return jackpotRewardRepository.findByJackpotIdAndBetIdAndUserId(jackpot.getId(), betDto.getUserId(), jackpot.getId())
                .orElseGet(() -> {
                    JackpotReward jackpotReward = new JackpotReward();
                    jackpotReward.setBetId(betDto.getBetId());
                    jackpotReward.setUserId(jackpot.getId());
                    jackpotReward.setJackpot(jackpot);
                    jackpotReward.setJackpotRewardAmount(jackpot.getTotalAmount());
                    return jackpotRewardRepository.save(jackpotReward);
                });
    }


    private boolean isWin(Jackpot jackpot) {
        JackpotRewardConfiguration jackpotRewardConfiguration = jackpot.getRewardConfiguration();
        RewardStrategy rewardStrategy = rewardStrategyFactory.getStrategy(jackpotRewardConfiguration.getContributorType());
        return rewardStrategy.isWin(jackpot);
    }
}
