package com.sporty.test.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "jackpot_contribution")
public class JackpotContribution {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "jackpot_id")
    private Jackpot jackpot;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "bet_id")
    private Long betId;

    @Column(name = "stake_amount")
    private Double stakeAmount;

    @Column(name = "contribution_amount")
    private Double contributionAmount;

    @Column(name = "current_jackpot_amount")
    private Double currentJackpotAmount;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

}
