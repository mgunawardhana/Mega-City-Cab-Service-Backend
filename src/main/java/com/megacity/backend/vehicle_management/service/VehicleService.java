package com.megacity.backend.vehicle_management.service;

import com.megacity.backend.domain.entity.Vehicle;
import com.megacity.backend.domain.response.APIResponse;
import org.springframework.http.ResponseEntity;

public interface VehicleService {

    ResponseEntity<APIResponse> registerVehicle(Vehicle item);

    ResponseEntity<APIResponse> updateVehicle(Vehicle item);

    ResponseEntity<APIResponse> deleteVehicle(Long itemId);

    ResponseEntity<APIResponse> fetchAllVehicle();

    ResponseEntity<APIResponse> fetchVehicleById(Long itemId);
}
