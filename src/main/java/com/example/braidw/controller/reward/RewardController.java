package com.example.braidw.controller.reward;

import com.example.braidw.dto.reward.RewardRequest;
import com.example.braidw.dto.reward.RewardResponse;
import com.example.braidw.dto.reward.RewardPointResponse;
import com.example.braidw.security.SecurityUtils;
import com.example.braidw.service.reward.RewardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rewards")
@RequiredArgsConstructor
public class RewardController {
    
    private final RewardService rewardService;

    @PostMapping
    public ResponseEntity<RewardResponse> createReward(@RequestBody RewardRequest request) {
        String userId = SecurityUtils.getCurrentUserId();
        request.setUserId(userId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(rewardService.createReward(request));
    }

    @GetMapping("/points")
    public ResponseEntity<RewardPointResponse> getCurrentPoints() {
        String userId = SecurityUtils.getCurrentUserId();
        return ResponseEntity.ok(rewardService.getCurrentPoints(userId));
    }

    @PutMapping("/points")
    public ResponseEntity<RewardResponse> adjustPoints(@RequestBody RewardRequest request) {
        String userId = SecurityUtils.getCurrentUserId();
        request.setUserId(userId);
        return ResponseEntity.ok(rewardService.adjustPoints(userId, request));
    }

    @DeleteMapping("/points")
    public ResponseEntity<RewardResponse> resetPoints() {
        String userId = SecurityUtils.getCurrentUserId();
        return ResponseEntity.ok(rewardService.resetPoints(userId));
    }

    @GetMapping("/history")
    public ResponseEntity<List<RewardResponse>> getUserRewards() {
        String userId = SecurityUtils.getCurrentUserId();
        return ResponseEntity.ok(rewardService.getUserRewards(userId));
    }
} 