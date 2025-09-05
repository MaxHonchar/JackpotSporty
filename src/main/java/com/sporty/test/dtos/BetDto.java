package com.sporty.test.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BetDto {
    @NotNull
    private Long betId;
    @NotNull
    private Long jackpotId;
    @NotNull
    private Long userId;
    @Min(1)
    private Double betAmount;
}
