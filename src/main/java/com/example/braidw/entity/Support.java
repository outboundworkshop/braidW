package com.example.braidw.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "supports")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Support {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    
    private String institution;
    
    private String amount;
    
    @Column(name = "interest_rate")
    private String interestRate;  // null for non-loan type
    
    private LocalDate deadline;
    
    private String status;
    
    @Enumerated(EnumType.STRING)
    private SupportType type;  // LOAN or GOVERNMENT_SUPPORT
    
    private int recommendation;
    
    @ElementCollection
    @CollectionTable(name = "support_reasons", joinColumns = @JoinColumn(name = "support_id"))
    @Column(name = "reason")
    private List<String> reasons;
    
    private String image;
    
    public enum SupportType {
        LOAN("대출"),
        GOVERNMENT_SUPPORT("정부지원");
        
        private final String displayName;
        
        SupportType(String displayName) {
            this.displayName = displayName;
        }
        
        public String getDisplayName() {
            return displayName;
        }
    }
} 