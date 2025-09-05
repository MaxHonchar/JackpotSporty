package com.sporty.test.dtos;

import lombok.Data;

@Data
public class RewardDto {

    private Long jackpotId;
    private Long userId;
    private Double rewardAmount;

}
