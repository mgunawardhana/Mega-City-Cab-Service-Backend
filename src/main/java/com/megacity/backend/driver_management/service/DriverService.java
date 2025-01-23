package com.megacity.backend.driver_management.service;

import com.megacity.backend.domain.entity.Driver;
import com.megacity.backend.domain.response.APIResponse;
import org.springframework.http.ResponseEntity;

public interface DriverService {

    /**
     * Registers a new driver.
     *
     * @param driver the driver to be registered
     * @return a ResponseEntity containing the APIResponse
     */
    ResponseEntity<APIResponse> registerDriver(Driver driver);

    /**
     * Retrieves a driver by their NIC.
     *
     * @param driverNIC the NIC of the driver to be retrieved
     * @return a ResponseEntity containing the APIResponse with the driver details
     */
    ResponseEntity<APIResponse> getDriverById(String driverNIC);

    /**
     * Retrieves all drivers by their license number.
     * @return a ResponseEntity containing the APIResponse with the list of drivers
     */
    ResponseEntity<APIResponse> getAllDrivers();

    /**
     * Deletes a driver by their license number.
     *
     * @param licenseNumber the license number of the driver to be deleted
     * @return a ResponseEntity containing the APIResponse
     */
    ResponseEntity<APIResponse> deleteDriverById(String licenseNumber);

    /**
     * Updates an existing driver.
     *
     * @param driver the driver to be updated
     * @return a ResponseEntity containing the APIResponse
     */
    ResponseEntity<APIResponse> updateDriver(Driver driver);
}
