package com.megacity.backend.driver_management.service;

import com.megacity.backend.domain.entity.Driver;
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
}
