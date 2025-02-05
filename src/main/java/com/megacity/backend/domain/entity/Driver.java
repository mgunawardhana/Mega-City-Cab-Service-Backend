package com.megacity.backend.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "driver_registration_number")
    private Integer driverRegistrationNumber;

    @Column(name = "driver_first_name", nullable = false)/** HOLD */
    private String driverFirstName;

    @Column(name = "driver_profile_picture", nullable = false)
    private String driverProfilePicture;

    @Column(name = "driver_last_name", nullable = false)/** HOLD */
    private String driverLastName;

    @Column(name = "driver_nic", nullable = false)/** HOLD */
    private String driverNIC;

    @Column(name = "phone_number", nullable = false)/** HOLD */
    private String phoneNumber;

    @Column(name = "email_address")/** HOLD */
    private String emailAddress;

    @Column(name = "license_number", nullable = false)/** HOLD */
    private String licenseNumber;

    @Column(name = "license_expiry_date")
    private Date licenseExpiryDate;

    @Column(name = "driver_address")/** HOLD */
    private String driverAddress;

    @Column(name = "vehicle_assigned")
    private String vehicleAssigned = "FALSE";

    @Column(name = "driver_status", nullable = false)
    private String driverStatus = "Active";

    @Column(name = "emergency_contact")
    private String emergencyContact;

    @Column(name = "date_of_birth", nullable = false)
    private Date dateOfBirth;

    @Column(name = "date_of_joining")
    private Date dateOfJoining;
}