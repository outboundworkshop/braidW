package com.example.braidw.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignupRequest {

    @NotBlank
    private String userId;
    
    @NotBlank
    @Size(min = 3, max = 100)
    private String username;
    
    @NotBlank
    @Size(max = 100)
    @Email
    private String email;
    
    @Size(max = 100)
    private String location;
    
    private Boolean consentGiven;
    
    @NotBlank
    @Size(min = 6, max = 40)
    private String password;
} 