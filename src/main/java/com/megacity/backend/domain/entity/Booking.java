package com.megacity.backend.domain.entity;


import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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

    @Column(name = "destination_details", nullable = false)
    private String destinationDetails;

    @Column(name = "booking_date", nullable = false)
    private LocalDateTime bookingDate;

    @Column(name = "pickup_location", nullable = false)
    private String pickupLocation;

    @Column(name = "drop_off_location", nullable = false)
    private String dropOffLocation;

    @Column(name = "car_number", nullable = false)
    private String carNumber;

    @Column(name = "fare", nullable = false, precision = 10, scale = 2)
    private BigDecimal fare;

    @Column(name = "taxes", nullable = false, precision = 10, scale = 2)
    private BigDecimal taxes;

    @Column(name = "discount", precision = 10, scale = 2)
    private BigDecimal discount;

    @Column(name = "total_amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalAmount;


    /** customer related details for booking */
    @Column(name = "customer_registration_number", nullable = false)
    private String customerRegistrationNumber;

    @Column(name = "customer_name", nullable = false)
    private String customerName;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "telephone_number", nullable = false)
    private String telephoneNumber;

    @Column(name = "nic", nullable = false, unique = true)
    private String nic;

    /** driver related details for booking */
    @Column(name = "driver_id", nullable = false)
    private String driverId;

}
