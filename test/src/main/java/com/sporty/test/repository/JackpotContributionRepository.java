package com.sporty.test.repository;

import com.sporty.test.domain.Jackpot;
import com.sporty.test.domain.JackpotContribution;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JackpotContributionRepository extends CrudRepository<JackpotContribution, Long> {
    Optional<JackpotContribution> findByJackpotIdAndBetIdAndUserId(Long jackpotId, Long betId, Long userId);

    List<JackpotContribution> findByJackpot(Jackpot jackpot);
}
