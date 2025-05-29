package com.example.braidw.service.auth;

import com.example.braidw.dto.auth.SignupRequest;
import com.example.braidw.entity.User;
import com.example.braidw.repository.auth.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public String registerUser(SignupRequest signUpRequest) {
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new RuntimeException("Error: Email is already in use!");
        }

        // Create new user's account
        User user = User.builder()
                .id(signUpRequest.getUserId())
                .username(signUpRequest.getUsername())
                .email(signUpRequest.getEmail())
                .location(signUpRequest.getLocation())
                .consentGiven(signUpRequest.getConsentGiven() != null ? signUpRequest.getConsentGiven() : false)
                .password(passwordEncoder.encode(signUpRequest.getPassword()))
                .build();

        userRepository.save(user);

        return "User registered successfully!";
    }
} 