package com.sporty.test.repository;

import com.sporty.test.domain.Jackpot;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface JackpotRepository extends CrudRepository<Jackpot, Long> {
}
