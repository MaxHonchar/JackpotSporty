package com.sporty.test.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "jackpot_reward")
public class JackpotReward {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "jackpot_id")
    private Jackpot jackpot;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "bet_id")
    private Long betId;

    @Column(name = "jackpot_reward_amount")
    private Double jackpotRewardAmount;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

}
