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
           "(:industry IS NOT NULL AND s.industry = :industry) OR " +
           "(:scale IS NOT NULL AND s.scale = :scale) OR " +
           "(:companyAge IS NOT NULL AND s.preferredEstablishedYears IS NOT NULL AND :companyAge >= s.preferredEstablishedYears) OR " +
           "(:revenueRange IS NOT NULL AND s.revenueRange = :revenueRange) " +
           "ORDER BY s.recommendation DESC")
    List<Support> findByRecommendationCriteria(
            @Param("industry") String industry,
            @Param("scale") String scale,
            @Param("companyAge") Integer companyAge,
            @Param("revenueRange") String revenueRange
    );
} 