package com.example.braidw.service.eventpromotion;

import com.example.braidw.dto.eventpromotion.EventPromotionRequest;
import com.example.braidw.entity.EventPromotion;
import com.example.braidw.entity.User;
import com.example.braidw.repository.eventpromotion.EventPromotionRepository;
import com.example.braidw.repository.auth.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
public class EventPromotionService {

    @Autowired
    private EventPromotionRepository eventPromotionRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public Long createEvent(EventPromotionRequest request) {
             EventPromotion event = EventPromotion.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .startDate(LocalDate.parse(request.getStartDate()))
                .endDate(LocalDate.parse(request.getEndDate()))
                .build();
        
        EventPromotion savedEvent = eventPromotionRepository.save(event);
        return savedEvent.getEventId();
    }

    @Transactional(readOnly = true)
    public List<EventPromotion> getAllEvents(String userId) {
        return eventPromotionRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<EventPromotion> getActiveEvents(String userId) {
        return eventPromotionRepository.findByEndDateAfterOrderByStartDateAsc(java.time.LocalDate.now());
    }

} 