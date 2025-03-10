package com.megacity.backend.service;

import com.megacity.backend.domain.entity.Driver;
import com.megacity.backend.domain.response.APIResponse;
import com.megacity.backend.driver_management.service.impl.DriverServiceImpl;
import com.megacity.backend.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Slf4j
public class DriverManagementServiceTest {

    private ResponseUtil responseUtils;
    private JdbcTemplate writeJdbcTemplate;
    private JdbcTemplate readJdbcTemplate;
    private DriverServiceImpl driverService;

    @BeforeEach
    void setUp() {
        responseUtils = mock(ResponseUtil.class);
        writeJdbcTemplate = mock(JdbcTemplate.class);
        readJdbcTemplate = mock(JdbcTemplate.class);
        driverService = new DriverServiceImpl(writeJdbcTemplate, readJdbcTemplate, responseUtils);
    }

    private Driver createTestDriver() {
        return Driver.builder()
                .driverRegistrationNumber(1)
                .driverNIC("123456789V")
                .phoneNumber("1234567890")
                .licenseNumber("LIC12345")
                .licenseExpiryDate(new Date())
                .driverAddress("123 Main Street")
                .vehicleAssigned("Vehicle 1")
                .driverStatus("Active")
                .emergencyContact("9876543210")
                .dateOfBirth(new Date())
                .dateOfJoining(new Date())
                .build();
    }
}
