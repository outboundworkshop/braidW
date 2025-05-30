package com.example.braidw.repository.kpi;

import com.example.braidw.entity.KpiData;
import com.example.braidw.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KpiDataRepository extends JpaRepository<KpiData, Long> {
    List<KpiData> findAllByOrderByCreatedAtDesc();

    @Query("SELECT kd FROM KpiData kd WHERE kd.user = :user ORDER BY kd.createdAt DESC")
    List<KpiData> findAllByUserOrderByCreatedAtDesc(@Param("user") User user);
} 