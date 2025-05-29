package com.example.braidw.controller.eventpromotion;

import com.example.braidw.dto.eventpromotion.EventPromotionRequest;
import com.example.braidw.entity.EventPromotion;
import com.example.braidw.security.SecurityUtils;
import com.example.braidw.service.eventpromotion.EventPromotionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
public class EventPromotionController {

    private final EventPromotionService eventPromotionService;

    @PostMapping
    public ResponseEntity<?> createEvent(@RequestBody EventPromotionRequest request) {
        Long eventId = eventPromotionService.createEvent(request);
        return ResponseEntity.status(201)
                .body(Map.of(
                    "event_id", eventId,
                    "code", 201,
                    "message", "이벤트 등록 성공"
                ));
    }

    @GetMapping
    public ResponseEntity<List<EventPromotion>> getAllEvents() {
        String userId = SecurityUtils.getCurrentUserId();
        return ResponseEntity.ok(eventPromotionService.getAllEvents(userId));
    }

    @GetMapping("/active")
    public ResponseEntity<List<EventPromotion>> getActiveEvents() {
        String userId = SecurityUtils.getCurrentUserId();
        return ResponseEntity.ok(eventPromotionService.getActiveEvents(userId));
    }

} 