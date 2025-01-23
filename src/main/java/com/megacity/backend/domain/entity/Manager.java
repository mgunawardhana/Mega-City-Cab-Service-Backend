package com.megacity.backend.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Manager {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer managerRegistrationNumber;

    private Integer rootUserId;

    private String managerAddress;

    private String managerNIC;

    @Column(nullable = false)
    private String phoneNumber;

    private LocalDateTime created_at;

    private LocalDateTime updated_at;
}
