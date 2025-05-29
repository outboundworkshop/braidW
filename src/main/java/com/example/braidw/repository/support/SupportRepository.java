package com.example.braidw.repository.support;

import com.example.braidw.entity.Support;
import com.example.braidw.entity.Support.SupportType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupportRepository extends JpaRepository<Support, Long> {
    List<Support> findByType(SupportType type);
    List<Support> findByTypeOrderByRecommendationDesc(SupportType type);
    List<Support> findAllByOrderByRecommendationDesc();

    @Query("SELECT s FROM Support s WHERE " +
           "(:industry IS NULL OR s.industry = :industry) AND " +
           "(:scale IS NULL OR s.scale = :scale) AND " +
           "(:foundedYear IS NULL OR s.foundedYear <= :foundedYear) AND " +
           "(:revenueRange IS NULL OR s.revenueRange = :revenueRange) " +
           "ORDER BY s.recommendation DESC")
    List<Support> findByRecommendationCriteria(
            @Param("industry") String industry,
            @Param("scale") String scale,
            @Param("foundedYear") Integer foundedYear,
            @Param("revenueRange") String revenueRange
    );
} 