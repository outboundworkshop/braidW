package com.example.braidw.repository.campaign;

import com.example.braidw.entity.Campaign;
import com.example.braidw.entity.Campaign.CampaignType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CampaignRepository extends JpaRepository<Campaign, Long> {
    List<Campaign> findByType(CampaignType type);
    List<Campaign> findByTypeAndStatus(CampaignType type, String status);
    List<Campaign> findByTypeOrderByScheduledTimeDesc(CampaignType type);
} 