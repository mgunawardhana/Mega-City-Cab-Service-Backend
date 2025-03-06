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
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "registration_number")
    private Integer registrationNumber;

    @Column(name = "root_user_id")
    private Integer rootUserId;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "nic", nullable = false)
    private String NIC;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;
}