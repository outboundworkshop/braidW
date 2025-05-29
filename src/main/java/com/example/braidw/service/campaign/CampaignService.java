//package com.example.braidw.service.campaign;
//
//import com.example.braidw.dto.campaign.CampaignResponse;
//import com.example.braidw.entity.Campaign;
//import com.example.braidw.entity.Notification;
//import com.example.braidw.repository.campaign.CampaignRepository;
//import com.example.braidw.repository.campaign.NotificationRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.time.LocalDateTime;
//import java.time.temporal.ChronoUnit;
//import java.util.Arrays;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//@RequiredArgsConstructor
//@Transactional(readOnly = true)
//public class CampaignService {
//
//    private final CampaignRepository campaignRepository;
//    private final NotificationRepository notificationRepository;
//
//    public CampaignResponse getDashboardData() {
//        List<Campaign> activeCampaigns = campaignRepository.findByTypeAndStatus(
//                Campaign.CampaignType.CAMPAIGN, "진행중");
//
//        return CampaignResponse.builder()
//                .campaigns(convertToCampaignDtos(activeCampaigns))
//                .notifications(getRecentNotifications())
//                .build();
//    }
//
//    private List<CampaignResponse.Campaign> convertToCampaignDtos(List<Campaign> campaigns) {
//        return campaigns.stream()
//                .map(campaign -> CampaignResponse.Campaign.builder()
//                        .title(campaign.getTitle())
//                        .status(campaign.getStatus())
//                        .statusColor(campaign.getStatusColor())
//                        .progress(campaign.getProgress())
//                        .stats(createStats(campaign))
//                        .target(campaign.getTarget())
//                        .scheduledTime(campaign.getScheduledTime().toString())
//                        .build())
//                .collect(Collectors.toList());
//    }
//
//    private List<CampaignResponse.Stat> createStats(Campaign campaign) {
//        return Arrays.asList(
//                CampaignResponse.Stat.builder()
//                        .label("발송")
//                        .value(String.valueOf(campaign.getSentCount()))
//                        .build(),
//                CampaignResponse.Stat.builder()
//                        .label("오픈")
//                        .value(String.valueOf(campaign.getOpenCount()))
//                        .build(),
//                CampaignResponse.Stat.builder()
//                        .label("클릭")
//                        .value(String.valueOf(campaign.getClickCount()))
//                        .build()
//        );
//    }
//
//    private List<CampaignResponse.Notification> getRecentNotifications() {
//        // 최근 24시간 내의 알림만 조회
//        LocalDateTime oneDayAgo = LocalDateTime.now().minus(24, ChronoUnit.HOURS);
//        List<Notification> notifications = notificationRepository
//                .findByCreatedAtAfterOrderByCreatedAtDesc(oneDayAgo);
//
//        return notifications.stream()
//                .map(notification -> CampaignResponse.Notification.builder()
//                        .title(notification.getTitle())
//                        .message(notification.getMessage())
//                        .time(formatTimeAgo(notification.getCreatedAt()))
//                        .action(notification.getAction())
//                        .borderColor(notification.getBorderColor())
//                        .build())
//                .collect(Collectors.toList());
//    }
//
//    private String formatTimeAgo(LocalDateTime dateTime) {
//        long hours = ChronoUnit.HOURS.between(dateTime, LocalDateTime.now());
//        if (hours < 1) {
//            long minutes = ChronoUnit.MINUTES.between(dateTime, LocalDateTime.now());
//            return minutes + "분 전";
//        }
//        if (hours < 24) {
//            return hours + "시간 전";
//        }
//        return "어제";
//    }
//}