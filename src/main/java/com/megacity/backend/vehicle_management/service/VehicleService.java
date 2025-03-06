package com.megacity.backend.vehicle_management.service;

import com.megacity.backend.domain.entity.Vehicle;
import com.megacity.backend.domain.response.APIResponse;
import org.springframework.http.ResponseEntity;

public interface VehicleService {

    /**
     * Registers a new vehicle in the system.
     *
     * @param vehicle the {@link Vehicle} object containing the vehicle details to be registered
     * @return a {@link ResponseEntity} containing an {@link APIResponse} with the result of the registration operation
     */
    ResponseEntity<APIResponse> registerVehicle(Vehicle vehicle);

    /**
     * Updates an existing vehicle in the system.
     *
     * @param vehicle the {@link Vehicle} object containing the updated vehicle details
     * @return a {@link ResponseEntity} containing an {@link APIResponse} with the result of the update operation
     */
    ResponseEntity<APIResponse> updateVehicle(Vehicle vehicle);

    /**
     * Deletes a vehicle from the system based on its ID.
     *
     * @param vehicleId the unique identifier of the vehicle to be deleted
     * @return a {@link ResponseEntity} containing an {@link APIResponse} with the result of the deletion operation
     */
    ResponseEntity<APIResponse> deleteVehicle(Long vehicleId);

    /**
     * Retrieves a paginated list of all vehicle records.
     *
     * @param page the page number to retrieve (zero-based)
     * @param size the number of vehicle records per page
     * @return a {@link ResponseEntity} containing an {@link APIResponse} with the list of vehicle records
     */
    ResponseEntity<APIResponse> fetchAllVehicle(int page, int size);

    /**
     * Retrieves a specific vehicle by its ID.
     *
     * @param vehicleId the unique identifier of the vehicle to fetch
     * @return a {@link ResponseEntity} containing an {@link APIResponse} with the requested vehicle details
     */
    ResponseEntity<APIResponse> fetchVehicleById(Long vehicleId);
}
