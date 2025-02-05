package com.megacity.backend.vehicle_management.service;

import com.megacity.backend.domain.entity.Vehicle;
import com.megacity.backend.domain.response.APIResponse;
import org.springframework.http.ResponseEntity;

public interface VehicleService {

    /**
     * Registers a new vehicle.
     *
     * @param vehicle the vehicle to be registered
     * @return a ResponseEntity containing the APIResponse
     */
    ResponseEntity<APIResponse> registerVehicle(Vehicle vehicle);

    /**
     * Updates an existing vehicle.
     *
     * @param vehicle the vehicle to be updated
     * @return a ResponseEntity containing the APIResponse
     */
    ResponseEntity<APIResponse> updateVehicle(Vehicle vehicle);

    /**
     * Deletes a vehicle by its ID.
     *
     * @param vehicleId the ID of the vehicle to be deleted
     * @return a ResponseEntity containing the APIResponse
     */
    ResponseEntity<APIResponse> deleteVehicle(Long vehicleId);

    /**
     * Fetches all vehicles.
     *
     * @return a ResponseEntity containing the APIResponse with the list of all vehicles
     */
    ResponseEntity<APIResponse> fetchAllVehicle(int page, int size);

    /**
     * Fetches a vehicle by its ID.
     *
     * @param vehicleId the ID of the vehicle to be fetched
     * @return a ResponseEntity containing the APIResponse with the vehicle details
     */
    ResponseEntity<APIResponse> fetchVehicleById(Long vehicleId);
}
