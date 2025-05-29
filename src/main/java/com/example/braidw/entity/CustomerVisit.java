package com.example.braidw.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "customer_visits")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerVisit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime visitDate;
    private Integer newCustomerCount;
    private Integer returningCustomerCount;
    
    @ElementCollection
    @CollectionTable(name = "customer_tags", joinColumns = @JoinColumn(name = "visit_id"))
    @Column(name = "tag")
    private List<String> customerTags;
} 