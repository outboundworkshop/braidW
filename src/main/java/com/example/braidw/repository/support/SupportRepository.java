package com.example.braidw.repository.support;

import com.example.braidw.entity.Support;
import com.example.braidw.entity.Support.SupportType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SupportRepository extends JpaRepository<Support, Long> {
    List<Support> findByType(SupportType type);
    List<Support> findByTypeOrderByRecommendationDesc(SupportType type);
    List<Support> findAllByOrderByRecommendationDesc();
} 