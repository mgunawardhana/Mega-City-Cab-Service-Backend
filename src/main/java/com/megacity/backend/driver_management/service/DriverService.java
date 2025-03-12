package com.megacity.backend.driver_management.service;

import com.megacity.backend.domain.response.APIResponse;
import org.springframework.http.ResponseEntity;

public interface DriverService {


    /**
     * Retrieves a driver by their NIC.
     *
     * @param driverNIC the NIC of the driver to be retrieved
     * @return a ResponseEntity containing the APIResponse with the driver details
     */
    ResponseEntity<APIResponse> getDriverById(String driverNIC);

    /**
     * Retrieves all drivers by their license number.
     *
     * @return a ResponseEntity containing the APIResponse with the list of drivers
     */
    ResponseEntity<APIResponse> getAllDrivers();

    ResponseEntity<APIResponse> updateDriverStatus(Integer rootUserId, String driverStatus);
}
