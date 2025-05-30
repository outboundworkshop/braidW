package com.example.braidw.service.customer;

import com.example.braidw.entity.CustomerVisit;
import com.example.braidw.entity.User;
import com.example.braidw.repository.customer.CustomerVisitRepository;
import com.example.braidw.repository.auth.UserRepository;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CustomerVisitService {
    
    private final CustomerVisitRepository customerVisitRepository;
    private final UserRepository userRepository;
    
    public List<String> getCustomerTags(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        return customerVisitRepository.findAllCustomerTags(user);
    }
    
    public CustomerVisitStats getVisitStats(String userId, LocalDateTime startDate, LocalDateTime endDate) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        List<CustomerVisit> visits = customerVisitRepository.findByVisitDateBetweenAndUserOrderByVisitDateAsc(user, startDate, endDate);
        
        return CustomerVisitStats.builder()
                .dates(visits.stream()
                        .map(visit -> visit.getVisitDate().format(DateTimeFormatter.ofPattern("MM/dd")))
                        .collect(Collectors.toList()))
                .newCustomers(visits.stream()
                        .map(CustomerVisit::getNewCustomerCount)
                        .collect(Collectors.toList()))
                .returningCustomers(visits.stream()
                        .map(CustomerVisit::getReturningCustomerCount)
                        .collect(Collectors.toList()))
                .build();
    }
    
    @Data
    @Builder
    public static class CustomerVisitStats {
        private List<String> dates;
        private List<Integer> newCustomers;
        private List<Integer> returningCustomers;
    }
    
    @Transactional
    public void recordVisit(CustomerVisit visit, String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        visit.setUser(user);
        customerVisitRepository.save(visit);
    }
} 