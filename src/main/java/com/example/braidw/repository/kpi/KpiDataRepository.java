package com.example.braidw.repository.kpi;

import com.example.braidw.entity.KpiData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KpiDataRepository extends JpaRepository<KpiData, Long> {
    List<KpiData> findAllByOrderByCreatedAtDesc();
} 