package com.megacity.backend.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

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

    @Column(name = "root_user_id")
    private Integer rootUserId;

    @Column(name = "driver_nic", nullable = false)
    private String driverNIC;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "license_number", nullable = false)
    private String licenseNumber;

    @Column(name = "license_expiry_date")
    private Date licenseExpiryDate;

    @Column(name = "driver_address")
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

    @Column(name = "license_image_front")
    private String licenseImageFront;

    @Column(name = "license_image_back")
    private String licenseImageBack;

    @Column(name = "user_profile_pic")
    private String user_profile_pic;
}