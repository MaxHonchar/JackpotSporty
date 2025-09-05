package com.sporty.test.resource;

import com.sporty.test.dtos.BetDto;
import com.sporty.test.dtos.RewardDto;
import com.sporty.test.service.IRewardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/reward")
public class RewardResource {

    private final IRewardService rewardService;

    @PostMapping
    public ResponseEntity<RewardDto> reward(@Valid @RequestBody BetDto betDto) {
        return rewardService.getJackpot(betDto)
                .map(jackpot -> rewardService.updateJackpot(jackpot, betDto))
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}
