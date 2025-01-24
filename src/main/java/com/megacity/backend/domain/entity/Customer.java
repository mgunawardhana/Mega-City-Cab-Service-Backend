package com.megacity.backend.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_registration_number")
    private Integer customerRegistrationNumber;

    @Column(name = "root_user_id")
    private Integer rootUserId;

    @Column(name = "customer_address",nullable = false)
    private String customerAddress;

    @Column(name = "customer_nic",nullable = false)
    private String customerNIC;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;
}