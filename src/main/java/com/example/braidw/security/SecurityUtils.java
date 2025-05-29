package com.example.braidw.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SecurityUtils {

    public static String getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("No authenticated user found");
        }

        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        }
        
        throw new RuntimeException("Invalid authentication principal");
    }

    public static void validateUser(String userId) {
        String currentUserId = getCurrentUserId();
        log.info("Current user id is {}", currentUserId);
        log.info("Current user id is {}", userId);
        if (!currentUserId.equals(userId)) {
            throw new RuntimeException("Unauthorized access: User ID mismatch");
        }
    }
} 