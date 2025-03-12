package com.megacity.backend.service;

import com.megacity.backend.domain.entity.Vehicle;
import com.megacity.backend.domain.response.APIResponse;
import com.megacity.backend.util.ResponseUtil;
import com.megacity.backend.vehicle_management.service.impl.VehicleServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class VehicleManagementTest {

    private JdbcTemplate writeJdbcTemplate;
    private JdbcTemplate readJdbcTemplate;
    private ResponseUtil responseUtil;
    private VehicleServiceImpl vehicleService;

    @BeforeEach
    void setUp() {
        writeJdbcTemplate = mock(JdbcTemplate.class);
        readJdbcTemplate = mock(JdbcTemplate.class);
        responseUtil = mock(ResponseUtil.class);
        vehicleService = new VehicleServiceImpl(writeJdbcTemplate, readJdbcTemplate, responseUtil);
    }

    private Vehicle createSampleVehicle() {
        return Vehicle.builder()
                .id(1L)
                .registrationNumber("REG123")
                .vehicleImage("image.jpg")
                .make("Toyota")
                .model("Camry")
                .yearOfManufacture(2020)
                .color("Blue")
                .fuelType("Petrol")
                .engineCapacity("2.5L")
                .chassisNumber("CH123456")
                .vehicleType("Sedan")
                .ownerName("John Doe")
                .ownerContact("1234567890")
                .ownerAddress("123 Main St")
                .insuranceProvider("ABC Insurance")
                .insurancePolicyNumber("POL123")
                .insuranceExpiryDate(LocalDate.now().plusYears(1))
                .seatingCapacity(5)
                .licensePlateNumber("ABC123")
                .permitType("Commercial")
                .airConditioning(true)
                .additionalFeatures("GPS, Leather Seats")
                .status("Active")
                .build();
    }

    @Test
    @DisplayName("Register vehicle - Success scenario")
    void registerVehicleSuccessTest() {
        Vehicle vehicle = createSampleVehicle();
        when(writeJdbcTemplate.update(anyString(), any(Object[].class))).thenReturn(1);
        when(responseUtil.wrapSuccess("Vehicle registered successfully", HttpStatus.OK))
                .thenReturn(new ResponseEntity<>(APIResponse.builder()
                        .statusMessage("Vehicle registered successfully")
                        .build(), HttpStatus.OK));

        ResponseEntity<APIResponse> response = vehicleService.registerVehicle(vehicle);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(writeJdbcTemplate, times(1)).update(anyString(), any(Object[].class));
        verify(responseUtil, times(1)).wrapSuccess("Vehicle registered successfully", HttpStatus.OK);
    }

    @Test
    @DisplayName("Register vehicle - Error scenario")
    void registerVehicleErrorTest() {
        Vehicle vehicle = createSampleVehicle();
        RuntimeException exception = new RuntimeException("DB error");
        when(writeJdbcTemplate.update(anyString(), any(Object[].class))).thenThrow(exception);
        when(responseUtil.wrapError("Failed to register vehicle", exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR))
                .thenReturn(new ResponseEntity<>(APIResponse.builder()
                        .statusMessage("Failed to register vehicle")
                        .statusCode("500")
                        .build(), HttpStatus.INTERNAL_SERVER_ERROR));

        ResponseEntity<APIResponse> response = vehicleService.registerVehicle(vehicle);

        assertNotNull(response);  // Add null check
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Failed to register vehicle", response.getBody().getStatusMessage());
        verify(writeJdbcTemplate, times(1)).update(anyString(), any(Object[].class));
    }

    @Test
    @DisplayName("Update vehicle - Success scenario")
    void updateVehicleSuccessTest() {
        Vehicle vehicle = createSampleVehicle();
        when(writeJdbcTemplate.update(anyString(), any(Object[].class))).thenReturn(1);
        when(responseUtil.wrapSuccess("Vehicle registered successfully", HttpStatus.OK))
                .thenReturn(new ResponseEntity<>(APIResponse.builder().build(), HttpStatus.OK));

        ResponseEntity<APIResponse> response = vehicleService.updateVehicle(vehicle);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @DisplayName("Delete vehicle - Success scenario")
    void deleteVehicleSuccessTest() {
        when(writeJdbcTemplate.update(anyString(), eq(1L))).thenReturn(1);
        when(responseUtil.wrapSuccess("Vehicle deleted successfully", HttpStatus.OK))
                .thenReturn(new ResponseEntity<>(APIResponse.builder().build(), HttpStatus.OK));

        ResponseEntity<APIResponse> response = vehicleService.deleteVehicle(1L);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @DisplayName("Delete vehicle - Invalid ID scenario")
    void deleteVehicleInvalidIdTest() {
        when(responseUtil.wrapError("Invalid Vehicle ID", "Vehicle ID must be a positive number", HttpStatus.BAD_REQUEST))
                .thenReturn(new ResponseEntity<>(APIResponse.builder().build(), HttpStatus.BAD_REQUEST));

        ResponseEntity<APIResponse> response = vehicleService.deleteVehicle(-1L);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    @DisplayName("Fetch vehicle by ID - Success scenario")
    void fetchVehicleByIdSuccessTest() {
        Vehicle vehicle = createSampleVehicle();
        when(readJdbcTemplate.queryForObject(anyString(), eq(new Object[]{1L}), any(RowMapper.class))).thenReturn(vehicle);
        when(responseUtil.wrapSuccess(vehicle, HttpStatus.OK))
                .thenReturn(new ResponseEntity<>(APIResponse.builder().result(vehicle).build(), HttpStatus.OK));

        ResponseEntity<APIResponse> response = vehicleService.fetchVehicleById(1L);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(vehicle, response.getBody().getResult());
    }

    @Test
    @DisplayName("Fetch vehicle by ID - Not found scenario")
    void fetchVehicleByIdNotFoundTest() {
        when(readJdbcTemplate.queryForObject(anyString(), eq(new Object[]{1L}), any(RowMapper.class)))
                .thenThrow(new EmptyResultDataAccessException(1));
        when(responseUtil.wrapError("Vehicle not found", "No vehicle found with the provided ID", HttpStatus.NOT_FOUND))
                .thenReturn(new ResponseEntity<>(APIResponse.builder().build(), HttpStatus.NOT_FOUND));

        ResponseEntity<APIResponse> response = vehicleService.fetchVehicleById(1L);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    @DisplayName("Fetch all vehicles - Success scenario")
    void fetchAllVehiclesSuccessTest() {
        List<Vehicle> vehicles = Collections.singletonList(createSampleVehicle());
        when(readJdbcTemplate.query(anyString(), eq(new Object[]{100, 0}), any(RowMapper.class))).thenReturn(vehicles);
        when(responseUtil.wrapSuccess(vehicles, HttpStatus.OK))
                .thenReturn(new ResponseEntity<>(APIResponse.builder().result(vehicles).build(), HttpStatus.OK));

        ResponseEntity<APIResponse> response = vehicleService.fetchAllVehicle(0, 100);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(vehicles, response.getBody().getResult());
    }

    @Test
    @DisplayName("Fetch all vehicles - Error scenario")
    void fetchAllVehiclesErrorTest() {
        RuntimeException exception = new RuntimeException("Query failed");
        when(readJdbcTemplate.query(anyString(), eq(new Object[]{100, 0}), any(RowMapper.class))).thenThrow(exception);
        when(responseUtil.wrapError("Failed to fetch all vehicles", exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR))
                .thenReturn(new ResponseEntity<>(APIResponse.builder()
                        .statusMessage("Failed to fetch all vehicles")
                        .statusCode("500")
                        .build(), HttpStatus.INTERNAL_SERVER_ERROR));

        ResponseEntity<APIResponse> response = vehicleService.fetchAllVehicle(0, 100);

        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Failed to fetch all vehicles", response.getBody().getStatusMessage());
    }
}