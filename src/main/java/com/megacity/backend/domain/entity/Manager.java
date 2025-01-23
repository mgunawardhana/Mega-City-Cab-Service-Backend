package com.megacity.backend.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Manager {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_registration_number")
    private Integer managerRegistrationNumber;

    @Column(name = "root_user_id")
    private Integer rootUserId;

    @Column(name = "customer_address",nullable = false)
    private String managerAddress;

    @Column(name = "customer_nic",nullable = false)
    private String managerNIC;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

}
