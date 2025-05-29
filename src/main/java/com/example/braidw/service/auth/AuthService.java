package com.example.braidw.service.auth;

import com.example.braidw.dto.auth.SignupRequest;

public interface AuthService {
    String registerUser(SignupRequest signupRequest);
} 