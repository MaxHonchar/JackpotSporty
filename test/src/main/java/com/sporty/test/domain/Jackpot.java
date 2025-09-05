package com.sporty.test.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "jackpot")
public class Jackpot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "configuration_id")
    private JackpotConfiguration configuration;

    @Column(name = "total_amount")
    private Double totalAmount;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "reward_configuration_id")
    private JackpotRewardConfiguration rewardConfiguration;

    @OneToMany(mappedBy = "jackpot", cascade = CascadeType.ALL)
    private Set<JackpotContribution> contributions = new HashSet<>();

}
