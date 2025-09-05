package com.sporty.test.repository;

import com.sporty.test.domain.JackpotReward;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JackpotRewardRepository extends CrudRepository<JackpotReward, Long> {
    Optional<JackpotReward> findByJackpotIdAndBetIdAndUserId(Long jackpotId, Long betId, Long userId);
}
