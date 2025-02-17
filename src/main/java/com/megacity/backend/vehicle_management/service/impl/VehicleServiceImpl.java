package com.megacity.backend.vehicle_management.service.impl;

import com.megacity.backend.constant.SqlQuery;
import com.megacity.backend.domain.entity.Vehicle;
import com.megacity.backend.domain.response.APIResponse;
import com.megacity.backend.util.ResponseUtil;
import com.megacity.backend.vehicle_management.service.VehicleService;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Slf4j
@Service
public class VehicleServiceImpl implements VehicleService {

    @NonNull
    private final JdbcTemplate writeJdbcTemplate;

    @NonNull
    private final JdbcTemplate readJdbcTemplate;

    @NonNull
    private final ResponseUtil responseUtil;

    public VehicleServiceImpl(@NonNull JdbcTemplate writeJdbcTemplate, @NonNull JdbcTemplate readJdbcTemplate, @NonNull ResponseUtil responseUtil) {
        this.writeJdbcTemplate = writeJdbcTemplate;
        this.readJdbcTemplate = readJdbcTemplate;
        this.responseUtil = responseUtil;
    }

    @Override
    public ResponseEntity<APIResponse> registerVehicle(Vehicle vehicle) {
        try {
            writeJdbcTemplate.update(
                    SqlQuery.InsertQuery.ADD_NEW_VEHICLE,
                    vehicle.getRegistrationNumber(),
                    vehicle.getMake(),
                    vehicle.getModel(),
                    vehicle.getYearOfManufacture(),
                    vehicle.getColor(),
                    vehicle.getFuelType(),
                    vehicle.getEngineCapacity(),
                    vehicle.getChassisNumber(),
                    vehicle.getVehicleType(),
                    vehicle.getOwnerName(),
                    vehicle.getOwnerContact(),
                    vehicle.getOwnerAddress(),
                    vehicle.getInsuranceProvider(),
                    vehicle.getInsurancePolicyNumber(),
                    vehicle.getInsuranceExpiryDate(),
                    vehicle.getSeatingCapacity(),
                    vehicle.getLicensePlateNumber(),
                    vehicle.getPermitType(),
                    vehicle.isAirConditioning(),
                    vehicle.getVehicleImage(), // **Added missing vehicle_image field**
                    vehicle.getAdditionalFeatures()
            );
            log.info("Vehicle registered successfully ");
            return responseUtil.wrapSuccess("Vehicle registered successfully", HttpStatus.OK);
        } catch (Exception e) {
            log.warn("Failed to register vehicle {}", e.getMessage());
            return responseUtil.wrapError("Failed to registering vehicle", e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<APIResponse> updateVehicle(Vehicle vehicle) {
        try {
            writeJdbcTemplate.update(SqlQuery.UpdateQuery.UPDATE_VEHICLE,
                    vehicle.getRegistrationNumber(),
                    vehicle.getMake(),
                    vehicle.getModel(),
                    vehicle.getYearOfManufacture(),
                    vehicle.getColor(),
                    vehicle.getFuelType(),
                    vehicle.getEngineCapacity(),
                    vehicle.getChassisNumber(),
                    vehicle.getVehicleType(),
                    vehicle.getOwnerName(),
                    vehicle.getOwnerContact(),
                    vehicle.getOwnerAddress(),
                    vehicle.getInsuranceProvider(),
                    vehicle.getInsurancePolicyNumber(),
                    vehicle.getInsuranceExpiryDate(),
                    vehicle.getSeatingCapacity(),
                    vehicle.getLicensePlateNumber(),
                    vehicle.getPermitType(),
                    vehicle.isAirConditioning(),
                    vehicle.getVehicleImage(),
                    vehicle.getAdditionalFeatures(),
                    vehicle.getId()
            );
            log.info("Vehicle updated successfully ");
            return responseUtil.wrapSuccess("Vehicle registered successfully", HttpStatus.OK);
        } catch (Exception e) {
            log.warn("Failed to update vehicle {}", e.getMessage());
            return responseUtil.wrapError("Failed to update vehicle", e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<APIResponse> deleteVehicle(Long itemId) {
        if (itemId == null || itemId <= 0) {
            log.warn("Invalid Vehicle ID");
            return responseUtil.wrapError("Invalid Vehicle ID", "Vehicle ID must be a positive number", HttpStatus.BAD_REQUEST);
        }
        try {
            writeJdbcTemplate.update(SqlQuery.DeleteQuery.DELETE_VEHICLE, itemId);
            log.info("Vehicle deleted successfully {}", itemId);
            return responseUtil.wrapSuccess("Vehicle deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            log.warn("Failed to delete vehicle {}", e.getMessage());
            return responseUtil.wrapError("Failed to delete vehicle", e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<APIResponse> fetchVehicleById(Long vehicleId) {
        if (vehicleId == null || vehicleId <= 0) {
            log.warn("Invalid Vehicle Id: {}", vehicleId);
            return responseUtil.wrapError("Invalid Vehicle ID", "Vehicle ID must be a positive number", HttpStatus.BAD_REQUEST);
        }

        try {
            Vehicle vehicle = readJdbcTemplate.queryForObject(SqlQuery.SelectQuery.FETCH_VEHICLE_BY_ID, new Object[]{vehicleId}, (rs, rowNum) -> Vehicle.builder().id(rs.getLong("id")).registrationNumber(rs.getString("registration_number")).make(rs.getString("make")).model(rs.getString("model")).yearOfManufacture(rs.getInt("year_of_manufacture")).color(rs.getString("color")).fuelType(rs.getString("fuel_type")).engineCapacity(rs.getString("engine_capacity")).chassisNumber(rs.getString("chassis_number")).vehicleType(rs.getString("vehicle_type")).ownerName(rs.getString("owner_name")).ownerContact(rs.getString("owner_contact")).ownerAddress(rs.getString("owner_address")).insuranceProvider(rs.getString("insurance_provider")).insurancePolicyNumber(rs.getString("insurance_policy_number")).insuranceExpiryDate(rs.getDate("insurance_expiry_date") != null ? rs.getDate("insurance_expiry_date").toLocalDate() : null).seatingCapacity(rs.getInt("seating_capacity")).licensePlateNumber(rs.getString("license_plate_number")).permitType(rs.getString("permit_type")).airConditioning(rs.getBoolean("air_conditioning")).additionalFeatures(rs.getString("additional_features")).build());
            log.info("Vehicle fetched successfully: {}", vehicle);
            return responseUtil.wrapSuccess(vehicle, HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            log.warn("No vehicle found with ID: {}", vehicleId);
            return responseUtil.wrapError("Vehicle not found", "No vehicle found with the provided ID", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.error("Error occurred while fetching vehicle by ID: {}", vehicleId, e);
            return responseUtil.wrapError("Failed to fetch vehicle!", e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @Override
    public ResponseEntity<APIResponse> fetchAllVehicle(@RequestParam(defaultValue = "0") int page,
                                                       @RequestParam(defaultValue = "100") int size) {
        try {
            List<Vehicle> vehicleList = readJdbcTemplate.query(SqlQuery.SelectQuery.FETCH_ALL_VEHICLE,
                    new Object[]{size, page * size},
                    (rs, rowNum) -> Vehicle.builder()
                            .id(rs.getLong("id"))
                            .vehicleImage(rs.getString("vehicle_image"))
                            .registrationNumber(rs.getString("registration_number"))
                            .make(rs.getString("make"))
                            .model(rs.getString("model"))
                            .yearOfManufacture(rs.getInt("year_of_manufacture"))
                            .color(rs.getString("color"))
                            .fuelType(rs.getString("fuel_type"))
                            .engineCapacity(rs.getString("engine_capacity"))
                            .chassisNumber(rs.getString("chassis_number"))
                            .vehicleType(rs.getString("vehicle_type"))
                            .ownerName(rs.getString("owner_name"))
                            .ownerContact(rs.getString("owner_contact"))
                            .ownerAddress(rs.getString("owner_address"))
                            .insuranceProvider(rs.getString("insurance_provider"))
                            .insurancePolicyNumber(rs.getString("insurance_policy_number"))
                            .insuranceExpiryDate(rs.getDate("insurance_expiry_date") != null ? rs
                                    .getDate("insurance_expiry_date").toLocalDate() : null)
                            .seatingCapacity(rs.getInt("seating_capacity"))
                            .licensePlateNumber(rs.getString("license_plate_number"))
                            .permitType(rs.getString("permit_type"))
                            .airConditioning(rs.getBoolean("air_conditioning"))
                            .additionalFeatures(rs.getString("additional_features"))
                            .build());
            log.info("All vehicles fetched successfully. Total: {}", vehicleList.size());
            return responseUtil.wrapSuccess(vehicleList, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error occurred while fetching all vehicles: ", e);
            return responseUtil.wrapError("Failed to fetch all vehicles", e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
