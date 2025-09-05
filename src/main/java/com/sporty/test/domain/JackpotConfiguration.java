package com.sporty.test.domain;

import com.sporty.test.domain.enums.ContributorType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "jackpot_configuration")
public class JackpotConfiguration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "configuration")
    private Jackpot jackpot;

    @Column(name = "initial_pool")
    private Double initialPool;

    @Column
    @Enumerated(EnumType.STRING)
    private ContributorType contributorType = ContributorType.FIXED;

    @Column(name = "fixed_percentage")
    private Double fixedPercentage;

    @Column(name = "min_percentage")
    private Double minPercentage;

    @Column(name = "max_percentage")
    private Double maxPercentage;

    @Column(name = "threshold_amount")
    private Double thresholdAmount;

}
