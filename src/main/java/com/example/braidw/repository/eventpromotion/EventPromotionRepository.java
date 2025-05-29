package com.example.braidw.repository;

import com.example.braidw.entity.EventPromotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EventPromotionRepository extends JpaRepository<EventPromotion, Long> {
    List<EventPromotion> findByEndDateAfterOrderByStartDateAsc(LocalDate currentDate);
}