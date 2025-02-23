package com.megacity.backend.service;

import com.megacity.backend.domain.entity.Driver;
import com.megacity.backend.domain.response.APIResponse;
import com.megacity.backend.driver_management.service.impl.DriverServiceImpl;
import com.megacity.backend.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Slf4j
public class DriverManagementServiceTest {

    private final ResponseUtil responseUtils = mock(ResponseUtil.class);
    private final JdbcTemplate writeJdbcTemplate = mock(JdbcTemplate.class);
    private final JdbcTemplate readJdbcTemplate = mock(JdbcTemplate.class);

    private final DriverServiceImpl driverService = new DriverServiceImpl(
            writeJdbcTemplate, readJdbcTemplate, responseUtils);

    private Driver createTestDriver() {
        return Driver.builder()
                .driverRegistrationNumber(1)
                .driverFirstName("John")
                .driverLastName("Doe")
                .driverNIC("123456789V")
                .phoneNumber("1234567890")
                .emailAddress("johndoe@example.com")
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
    @DisplayName("Register driver success scenario")
    void registerDriverSuccess() {
        Driver driver = createTestDriver();
        when(writeJdbcTemplate.update(anyString(), any(Object[].class))).thenReturn(1);
        when(responseUtils.wrapSuccess(anyString(), eq(HttpStatus.OK)))
                .thenReturn(new ResponseEntity<>(APIResponse.builder().build(), HttpStatus.OK));

        ResponseEntity<APIResponse> response = driverService.registerDriver(driver);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    @DisplayName("Register driver error scenario")
    void registerDriverError() {
        Driver driver = createTestDriver();
        when(writeJdbcTemplate.update(anyString(), any(Object[].class))).thenThrow(new RuntimeException("Database error"));
        when(responseUtils.wrapError(anyString(), anyString(), eq(HttpStatus.INTERNAL_SERVER_ERROR)))
                .thenReturn(new ResponseEntity<>(APIResponse.builder().build(), HttpStatus.INTERNAL_SERVER_ERROR));

        ResponseEntity<APIResponse> response = driverService.registerDriver(driver);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    @DisplayName("Update driver success scenario")
    void updateDriverSuccess() {
        Driver driver = createTestDriver();
        when(writeJdbcTemplate.update(anyString(), any(Object[].class))).thenReturn(1);
        when(responseUtils.wrapSuccess(anyString(), eq(HttpStatus.OK)))
                .thenReturn(new ResponseEntity<>(APIResponse.builder().build(), HttpStatus.OK));

        ResponseEntity<APIResponse> response = driverService.updateDriver(driver);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    @DisplayName("Update driver error scenario")
    void updateDriverError() {
        Driver driver = createTestDriver();
        when(writeJdbcTemplate.update(anyString(), any(Object[].class))).thenThrow(new RuntimeException("Database error"));
        when(responseUtils.wrapError(anyString(), anyString(), eq(HttpStatus.INTERNAL_SERVER_ERROR)))
                .thenReturn(new ResponseEntity<>(APIResponse.builder().build(), HttpStatus.INTERNAL_SERVER_ERROR));

        ResponseEntity<APIResponse> response = driverService.updateDriver(driver);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    @DisplayName("Delete driver success scenario")
    void deleteDriverSuccess() {
        when(writeJdbcTemplate.update(anyString(), any(Object[].class))).thenReturn(1);
        when(responseUtils.wrapSuccess(anyString(), eq(HttpStatus.OK)))
                .thenReturn(new ResponseEntity<>(APIResponse.builder().build(), HttpStatus.OK));

        ResponseEntity<APIResponse> response = driverService.deleteDriverById("123123123123");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    @DisplayName("Delete driver error scenario")
    void deleteDriverError() {
        when(writeJdbcTemplate.update(anyString(), any(Object[].class))).thenThrow(new RuntimeException("Database error"));
        when(responseUtils.wrapError(anyString(), anyString(), eq(HttpStatus.INTERNAL_SERVER_ERROR)))
                .thenReturn(new ResponseEntity<>(APIResponse.builder().build(), HttpStatus.INTERNAL_SERVER_ERROR));

        ResponseEntity<APIResponse> response = driverService.deleteDriverById("123123213");
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    @DisplayName("Fetch driver by ID success scenario")
    void fetchDriverByIdSuccess() {
        Driver driver = createTestDriver();
        when(readJdbcTemplate.queryForObject(anyString(), any(Object[].class), any(RowMapper.class))).thenReturn(driver);
        when(responseUtils.wrapSuccess(any(Driver.class), eq(HttpStatus.OK)))
                .thenReturn(new ResponseEntity<>(APIResponse.builder().build(), HttpStatus.OK));

        ResponseEntity<APIResponse> response = driverService.getDriverById("123123");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    @DisplayName("Fetch driver by ID error scenario")
    void fetchDriverByIdError() {
        when(readJdbcTemplate.queryForObject(anyString(), any(Object[].class), any(RowMapper.class))).thenThrow(new RuntimeException("Database error"));
        when(responseUtils.wrapError(anyString(), anyString(), eq(HttpStatus.INTERNAL_SERVER_ERROR)))
                .thenReturn(new ResponseEntity<>(APIResponse.builder().build(), HttpStatus.INTERNAL_SERVER_ERROR));

        ResponseEntity<APIResponse> response = driverService.getDriverById("123123sada");
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    @DisplayName("Fetch all drivers success scenario")
    void fetchAllDriversSuccess() {
        List<Driver> drivers = Collections.singletonList(createTestDriver());
        when(readJdbcTemplate.query(anyString(), any(Object[].class), any(RowMapper.class))).thenReturn(drivers);
        when(responseUtils.wrapSuccess(anyList(), eq(HttpStatus.OK)))
                .thenReturn(new ResponseEntity<>(APIResponse.builder().build(), HttpStatus.OK));

        ResponseEntity<APIResponse> response = driverService.getAllDrivers();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    @DisplayName("Fetch all drivers error scenario")
    void fetchAllDriversError() {
        when(readJdbcTemplate.query(anyString(), any(RowMapper.class)))
                .thenThrow(new RuntimeException("Database error"));
        when(responseUtils.wrapError(
                eq("Failed to fetch all drivers"),
                anyString(),
                eq(HttpStatus.INTERNAL_SERVER_ERROR)))
                .thenReturn(new ResponseEntity<>(APIResponse.builder().build(), HttpStatus.INTERNAL_SERVER_ERROR));
        ResponseEntity<APIResponse> response = driverService.getAllDrivers();
        assertNotNull(response, "Response should not be null");
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
    }

}
