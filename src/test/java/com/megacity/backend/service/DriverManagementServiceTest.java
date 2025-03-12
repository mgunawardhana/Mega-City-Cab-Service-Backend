package com.megacity.backend.service;

import com.megacity.backend.domain.entity.Driver;
import com.megacity.backend.domain.response.APIResponse;
import com.megacity.backend.util.ResponseUtil;
import com.megacity.backend.driver_management.service.impl.DriverServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.Date;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DriverManagementServiceTest {

    private JdbcTemplate writeJdbcTemplate;
    private JdbcTemplate readJdbcTemplate;
    private ResponseUtil responseUtil;
    private DriverServiceImpl driverService;

    @BeforeEach
    void setUp() {
        writeJdbcTemplate = mock(JdbcTemplate.class);
        readJdbcTemplate = mock(JdbcTemplate.class);
        responseUtil = mock(ResponseUtil.class);
        driverService = new DriverServiceImpl(writeJdbcTemplate, readJdbcTemplate, responseUtil);
    }

    private Driver createSampleDriver() {
        return Driver.builder()
                .driverRegistrationNumber(1)
                .rootUserId(100)
                .driverNIC("NIC123456")
                .phoneNumber("555-123-4567")
                .licenseNumber("LIC123")
                .licenseExpiryDate(Date.valueOf("2025-12-31"))
                .driverAddress("123 Driver St")
                .vehicleAssigned("VEH001")
                .driverStatus("AVAILABLE")
                .emergencyContact("555-987-6543")
                .dateOfBirth(Date.valueOf("1980-01-01"))
                .dateOfJoining(Date.valueOf("2020-01-01"))
                .licenseImageFront("front.jpg")
                .licenseImageBack("back.jpg")
                .user_profile_pic("profile.jpg")
                .build();
    }

    @Test
    @DisplayName("Update driver status - Success scenario")
    void updateDriverStatusSuccessTest() {
        String rootUserId = "100";
        String driverStatus = "AVAILABLE";
        when(readJdbcTemplate.update(anyString(), eq(driverStatus), eq(rootUserId))).thenReturn(1);
        when(responseUtil.wrapSuccess("Driver status updated successfully", HttpStatus.OK))
                .thenReturn(new ResponseEntity<>(APIResponse.builder()
                        .statusMessage("Driver status updated successfully")
                        .build(), HttpStatus.OK));

        ResponseEntity<APIResponse> response = driverService.updateDriverStatus(Integer.valueOf(rootUserId), driverStatus);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Driver status updated successfully", response.getBody().getStatusMessage());
        verify(readJdbcTemplate, times(1)).update(anyString(), eq(driverStatus), eq(rootUserId));
    }

    @Test
    @DisplayName("Update driver status - Driver not found scenario")
    void updateDriverStatusNotFoundTest() {
        String rootUserId = "100";
        String driverStatus = "AVAILABLE";
        when(readJdbcTemplate.update(anyString(), eq(driverStatus), eq(rootUserId))).thenReturn(0);
        when(responseUtil.wrapError("Not Found", "No driver found with rootUserId: " + rootUserId, HttpStatus.NOT_FOUND))
                .thenReturn(new ResponseEntity<>(APIResponse.builder()
                        .statusMessage("Not Found")
                        .build(), HttpStatus.NOT_FOUND));

        ResponseEntity<APIResponse> response = driverService.updateDriverStatus(Integer.valueOf(rootUserId), driverStatus);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Not Found", response.getBody().getStatusMessage());
    }

    @Test
    @DisplayName("Get all drivers - Success scenario")
    void getAllDriversSuccessTest() {
        List<Driver> drivers = Collections.singletonList(createSampleDriver());
        when(readJdbcTemplate.query(anyString(), any(RowMapper.class))).thenReturn(drivers);
        when(responseUtil.wrapSuccess(drivers, HttpStatus.OK))
                .thenReturn(new ResponseEntity<>(APIResponse.builder().result(drivers).build(), HttpStatus.OK));

        ResponseEntity<APIResponse> response = driverService.getAllDrivers();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(drivers, response.getBody().getResult());
        verify(readJdbcTemplate, times(1)).query(anyString(), any(RowMapper.class));
    }

    @Test
    @DisplayName("Get all drivers - Database error scenario")
    void getAllDriversDatabaseErrorTest() {
        DataAccessException exception = mock(DataAccessException.class);
        when(exception.getMessage()).thenReturn("DB error");
        when(readJdbcTemplate.query(anyString(), any(RowMapper.class))).thenThrow(exception);
        when(responseUtil.wrapError("Failed to fetch all drivers", "Database error: DB error", HttpStatus.INTERNAL_SERVER_ERROR))
                .thenReturn(new ResponseEntity<>(APIResponse.builder()
                        .statusMessage("Failed to fetch all drivers")
                        .build(), HttpStatus.INTERNAL_SERVER_ERROR));

        ResponseEntity<APIResponse> response = driverService.getAllDrivers();

        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Failed to fetch all drivers", response.getBody().getStatusMessage());
    }

    @Test
    @DisplayName("Get driver by ID - Success scenario")
    void getDriverByIdSuccessTest() {
        String driverRegNo = "1";
        Driver driver = createSampleDriver();
        when(readJdbcTemplate.queryForObject(anyString(), eq(new Object[]{driverRegNo}), any(RowMapper.class))).thenReturn(driver);
        when(responseUtil.wrapSuccess(driver, HttpStatus.OK))
                .thenReturn(new ResponseEntity<>(APIResponse.builder().result(driver).build(), HttpStatus.OK));

        ResponseEntity<APIResponse> response = driverService.getDriverById(driverRegNo);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(driver, response.getBody().getResult());
    }

    @Test
    @DisplayName("Get driver by ID - Error scenario")
    void getDriverByIdErrorTest() {
        String driverRegNo = "1";
        RuntimeException exception = new RuntimeException("Driver not found");
        when(readJdbcTemplate.queryForObject(anyString(), eq(new Object[]{driverRegNo}), any(RowMapper.class))).thenThrow(exception);
        when(responseUtil.wrapError("Failed to retrieve driver", exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR))
                .thenReturn(new ResponseEntity<>(APIResponse.builder()
                        .statusMessage("Failed to retrieve driver")
                        .build(), HttpStatus.INTERNAL_SERVER_ERROR));

        ResponseEntity<APIResponse> response = driverService.getDriverById(driverRegNo);

        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Failed to retrieve driver", response.getBody().getStatusMessage());
    }
}