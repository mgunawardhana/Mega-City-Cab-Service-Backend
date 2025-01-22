package com.megacity.backend.vehicle_management.service.impl;

import com.megacity.backend.domain.entity.Vehicle;
import com.megacity.backend.domain.response.APIResponse;
import com.megacity.backend.util.ResponseUtil;
import com.megacity.backend.vehicle_management.repository.VehicleRepository;
import com.megacity.backend.vehicle_management.service.VehicleService;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class VehicleServiceImpl implements VehicleService {

    @NonNull
    private final VehicleRepository vehicleRepository;

    @NonNull
    private final ResponseUtil responseUtil;

    public VehicleServiceImpl(@NonNull VehicleRepository vehicleRepository, @NonNull ResponseUtil responseUtil) {
        this.vehicleRepository = vehicleRepository;
        this.responseUtil = responseUtil;
    }

    @Override
    public ResponseEntity<APIResponse> registerVehicle(Vehicle vehicle) {
        try {
            Vehicle save = vehicleRepository.save(vehicle);
            log.info("Vehicle registered successfully {}",save);
            return responseUtil.wrapSuccess(save, HttpStatus.OK);
        } catch (Exception e) {
            log.warn("Failed to register vehicle {}", e.getMessage());
            return responseUtil.wrapError("Failed to registering vehicle", e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<APIResponse> updateVehicle(Vehicle vehicle) {
        try {
            if (vehicleRepository.existsById(Math.toIntExact(vehicle.getId()))) {
                Vehicle updatedVehicle = vehicleRepository.save(vehicle);
                log.info("Vehicle updated successfully {}",updatedVehicle);
                return responseUtil.wrapSuccess(updatedVehicle, HttpStatus.OK);
            } else {
                log.warn("Vehicle not found");
                return responseUtil.wrapError("Vehicle not found", "Vehicle with ID " + vehicle.getId() + " does not exist", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            log.warn("Failed to update vehicle {}", e.getMessage());
            return responseUtil.wrapError("Failed to update vehicle", e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<APIResponse> deleteVehicle(Long itemId) {
        try {
            if (vehicleRepository.existsById(Math.toIntExact(itemId))) {
                vehicleRepository.deleteById(Math.toIntExact(itemId));
                log.info("Vehicle deleted successfully {}",itemId);
                return responseUtil.wrapSuccess("Vehicle deleted successfully", HttpStatus.OK);
            } else {
                log.warn("Vehicle not found - {}",itemId);
                return responseUtil.wrapError("Vehicle not found", "Vehicle with ID " + itemId + " does not exist", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            log.warn("Failed to delete vehicle {}", e.getMessage());
            return responseUtil.wrapError("Failed to delete vehicle", e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<APIResponse> fetchVehicleById(Long vehicleId) {
        if (vehicleId == null || vehicleId <= 0) {
            log.warn("Invalid item ID");
            return responseUtil.wrapError("Invalid item ID", "Item ID must be a positive number", HttpStatus.BAD_REQUEST);
        }
        try {
            Vehicle vehicle = vehicleRepository.findById(Math.toIntExact(vehicleId)).orElse(null);
            log.info("Vehicle fetched successfully {}",vehicle);
            if (vehicle == null) {
                log.warn("Vehicle not found. fetchVehicleById - {}",vehicleId);
                return responseUtil.wrapError("Vehicle not found", "No vehicle found with the given ID", HttpStatus.NOT_FOUND);
            }
            return responseUtil.wrapSuccess(vehicle, HttpStatus.OK);
        } catch (Exception e) {
            log.warn("Failed to fetch vehicle {}", e.getMessage());
            return responseUtil.wrapError("Failed to fetch vehicle", e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<APIResponse> fetchAllVehicle() {
        try {
            List<Vehicle> all = vehicleRepository.findAll();
            log.info("All vehicles fetched successfully {}",all);
            return responseUtil.wrapSuccess(all, HttpStatus.OK);
        } catch (Exception e) {
            log.warn("Failed to fetch vehicles {}", e.getMessage());
            return responseUtil.wrapError("Failed to fetch vehicles!", e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
