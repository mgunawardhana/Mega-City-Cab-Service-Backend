package com.megacity.backend.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;


@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "vehicles")
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "registration_number", nullable = false, unique = true)
    private String registrationNumber;

    @Column(name = "make", nullable = false)
    private String make;

    @Column(name = "model", nullable = false)
    private String model;

    @Column(name = "year_of_manufacture", nullable = false)
    private int yearOfManufacture;

    @Column(name = "color")
    private String color;

    @Column(name = "fuel_type")
    private String fuelType;

    @Column(name = "engine_capacity")
    private String engineCapacity;

    @Column(name = "chassis_number", nullable = false, unique = true)
    private String chassisNumber;

    @Column(name = "vehicle_type", nullable = false)
    private String vehicleType;

    @Column(name = "owner_name", nullable = false)
    private String ownerName;

    @Column(name = "owner_contact", nullable = false)
    private String ownerContact;

    @Column(name = "owner_address")
    private String ownerAddress;

    @Column(name = "insurance_provider")
    private String insuranceProvider;

    @Column(name = "insurance_policy_number")
    private String insurancePolicyNumber;

    @Column(name = "insurance_expiry_date")
    private LocalDate insuranceExpiryDate;

    @Column(name = "seating_capacity", nullable = false)
    private int seatingCapacity;

    @Column(name = "license_plate_number", nullable = false, unique = true)
    private String licensePlateNumber;

    @Column(name = "permit_type")
    private String permitType;

    @Column(name = "air_conditioning")
    private boolean airConditioning;

    @Column(name = "vehicle_photo")
    private String vehiclePhoto;

    @Column(name = "additional_features")
    private String additionalFeatures;
}