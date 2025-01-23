package com.megacity.backend.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer customerRegistrationNumber;

    private Integer rootUserId;

    private String customerAddress;

    private String customerNIC;

    @Column(nullable = false)
    private String phoneNumber;

    private LocalDateTime created_at;

    private LocalDateTime updated_at;
}