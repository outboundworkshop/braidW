package com.example.braidw.service.reward;

import com.example.braidw.dto.reward.RewardRequest;
import com.example.braidw.dto.reward.RewardResponse;
import com.example.braidw.dto.reward.RewardPointResponse;
import com.example.braidw.entity.Reward;
import com.example.braidw.entity.User;
import com.example.braidw.repository.reward.RewardRepository;
import com.example.braidw.repository.auth.UserRepository;
import com.example.braidw.security.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
@Transactional
public class RewardService {
    private final RewardRepository rewardRepository;
    private final UserRepository userRepository;

    public RewardResponse createReward(RewardRequest request, String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        int points = calculatePoints(request.getAction());
        Reward reward = Reward.builder()
                .user(user)
                .points(points)
                .description(request.getAction())
                .build();
        rewardRepository.save(reward);
        int currentPoints = rewardRepository.findByUser(user).stream()
                .mapToInt(Reward::getPoints)
                .sum();
        return RewardResponse.builder()
                .message("포인트가 적립되었습니다.")
                .currentPoints(currentPoints)
                .build();
    }

    private int calculatePoints(String action) {
        return switch (action) {
            case "login_bonus" -> 100;
            case "daily_check" -> 50;
            case "review_write" -> 200;
            default -> throw new RuntimeException("Invalid action type");
        };
    }

    public RewardPointResponse getCurrentPoints(String userId) {
        SecurityUtils.validateUser(userId);
        
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        int points = rewardRepository.findByUser(user).stream()
                .mapToInt(Reward::getPoints)
                .sum();

        return RewardPointResponse.builder()
                .userId(userId)
                .points(points)
                .build();
    }

    public RewardResponse adjustPoints(String userId, RewardRequest request) {
        SecurityUtils.validateUser(userId);
        
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Reward reward = Reward.builder()
                .user(user)
                .points(calculatePoints(request.getAction()))
                .description("Point adjustment: " + request.getAction())
                .build();

        rewardRepository.save(reward);

        int currentPoints = rewardRepository.findByUser(user).stream()
                .mapToInt(Reward::getPoints)
                .sum();

        return RewardResponse.builder()
                .message("포인트가 수정되었습니다.")
                .currentPoints(currentPoints)
                .build();
    }

    public RewardResponse resetPoints(String userId) {
        SecurityUtils.validateUser(userId);
        
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        rewardRepository.deleteByUser(user);

        return RewardResponse.builder()
                .message("포인트가 초기화되었습니다.")
                .currentPoints(0)
                .build();
    }

    public List<RewardResponse> getUserRewards(String userId) {
        SecurityUtils.validateUser(userId);
        
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        return rewardRepository.findByUser(user).stream()
                .map(reward -> RewardResponse.builder()
                        .message(reward.getDescription())
                        .currentPoints(reward.getPoints())
                        .build())
                .collect(Collectors.toList());
    }
} 