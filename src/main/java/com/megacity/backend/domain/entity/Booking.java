package com.megacity.backend.domain.entity;


import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Booking {


    /** booking related details */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booking_number", nullable = false)
    private Long bookingNumber;

    @Column(name = "booking_date", nullable = false)
    private LocalDateTime bookingDate;

    @Column(name = "pickup_location", nullable = false)
    private String pickupLocation;

    @Column(name = "drop_off_location", nullable = false)
    private String dropOffLocation;

    @Column(name = "car_number", nullable = false)
    private String carNumber;

    @Column(name = "taxes", nullable = false, precision = 10, scale = 2)
    private BigDecimal taxes;

    @Column(name = "distance", nullable = false)
    private double distance;

    @Column(name = "estimatedTime", nullable = false)
    private double estimatedTime;

    @Column(name = "tax_without_cost", nullable = false)
    private double taxWithoutCost;

    @Column(name = "total_amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalAmount;

    @Column(name = "customer_registration_number", nullable = false)
    private String customerRegistrationNumber;

    /** driver related details for booking */
    @Column(name = "driver_id", nullable = false)
    private String driverId;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    @Column(name = "updated_date", nullable = false)
    private LocalDateTime updatedDate;

}
