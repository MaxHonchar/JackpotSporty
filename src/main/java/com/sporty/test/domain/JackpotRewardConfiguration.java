package com.sporty.test.domain;

import com.sporty.test.domain.enums.ContributorType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "jackpot_reward_configuration")
public class JackpotRewardConfiguration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "rewardConfiguration")
    private Jackpot jackpotReward;

    @Column(name = "contributor_type")
    @Enumerated(EnumType.STRING)
    private ContributorType contributorType = ContributorType.FIXED;

    @Column(name = "fixed_chance_reward")
    private Double fixedChanceReward;

    @Column(name = "min_chance_reward")
    private Double minChanceReward;

    @Column(name = "max_chance_reward")
    private Double maxChanceReward;

    @Column(name = "threshold_limit")
    private Double thresholdLimit;
}
