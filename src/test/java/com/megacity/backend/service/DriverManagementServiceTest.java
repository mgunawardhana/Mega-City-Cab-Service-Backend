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

    @Test
    @DisplayName("Fetch driver by ID success scenario")
    void fetchDriverByIdSuccess() {
        // Arrange
        Driver driver = createTestDriver();
        APIResponse expectedResponse = APIResponse.builder()
                .data(driver)
                .status(HttpStatus.OK.value())
                .build();

        when(readJdbcTemplate.queryForObject(anyString(), any(Object[].class), any(RowMapper.class)))
                .thenReturn(driver);
        when(responseUtils.wrapSuccess(eq(driver), eq(HttpStatus.OK)))
                .thenReturn(new ResponseEntity<>(expectedResponse, HttpStatus.OK));

        // Act
        ResponseEntity<APIResponse> response = driverService.getDriverById("123123");

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(expectedResponse, response.getBody());
        verify(readJdbcTemplate).queryForObject(anyString(), any(Object[].class), any(RowMapper.class));
        verify(responseUtils).wrapSuccess(eq(driver), eq(HttpStatus.OK));
    }

    @Test
    @DisplayName("Fetch driver by ID error scenario")
    void fetchDriverByIdError() {
        // Arrange
        String errorMessage = "Database error";
        APIResponse expectedResponse = APIResponse.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .error(errorMessage)
                .build();

        when(readJdbcTemplate.queryForObject(anyString(), any(Object[].class), any(RowMapper.class)))
                .thenThrow(new RuntimeException(errorMessage));
        when(responseUtils.wrapError(anyString(), eq(errorMessage), eq(HttpStatus.INTERNAL_SERVER_ERROR)))
                .thenReturn(new ResponseEntity<>(expectedResponse, HttpStatus.INTERNAL_SERVER_ERROR));

        // Act
        ResponseEntity<APIResponse> response = driverService.getDriverById("123123");

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(expectedResponse, response.getBody());
        verify(readJdbcTemplate).queryForObject(anyString(), any(Object[].class), any(RowMapper.class));
        verify(responseUtils).wrapError(anyString(), eq(errorMessage), eq(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @Test
    @DisplayName("Fetch all drivers success scenario")
    void fetchAllDriversSuccess() {
        // Arrange
        List<Driver> drivers = Collections.singletonList(createTestDriver());
        APIResponse expectedResponse = APIResponse.builder()
                .data(drivers)
                .status(HttpStatus.OK.value())
                .build();

        when(readJdbcTemplate.query(anyString(), any(RowMapper.class)))
                .thenReturn(drivers);
        when(responseUtils.wrapSuccess(eq(drivers), eq(HttpStatus.OK)))
                .thenReturn(new ResponseEntity<>(expectedResponse, HttpStatus.OK));

        // Act
        ResponseEntity<APIResponse> response = driverService.getAllDrivers();

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(expectedResponse, response.getBody());
        verify(readJdbcTemplate).query(anyString(), any(RowMapper.class));
        verify(responseUtils).wrapSuccess(eq(drivers), eq(HttpStatus.OK));
    }

    @Test
    @DisplayName("Fetch all drivers error scenario")
    void fetchAllDriversError() {
        // Arrange
        String errorMessage = "Database error";
        APIResponse expectedResponse = APIResponse.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .error(errorMessage)
                .build();

        when(readJdbcTemplate.query(anyString(), any(RowMapper.class)))
                .thenThrow(new RuntimeException(errorMessage));
        when(responseUtils.wrapError(eq("Failed to fetch all drivers"), eq(errorMessage), eq(HttpStatus.INTERNAL_SERVER_ERROR)))
                .thenReturn(new ResponseEntity<>(expectedResponse, HttpStatus.INTERNAL_SERVER_ERROR));

        // Act
        ResponseEntity<APIResponse> response = driverService.getAllDrivers();

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(expectedResponse, response.getBody());
        verify(readJdbcTemplate).query(anyString(), any(RowMapper.class));
        verify(responseUtils).wrapError(eq("Failed to fetch all drivers"), eq(errorMessage), eq(HttpStatus.INTERNAL_SERVER_ERROR));
    }
}