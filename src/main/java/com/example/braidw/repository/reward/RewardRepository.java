package com.example.braidw.repository.reward;

import com.example.braidw.entity.Reward;
import com.example.braidw.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RewardRepository extends JpaRepository<Reward, Long> {
    List<Reward> findByUser(User user);
    void deleteByUser(User user);
} 