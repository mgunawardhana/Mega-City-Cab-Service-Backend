package com.megacity.backend.driver_management.service.impl;

import com.megacity.backend.constant.SqlQuery;
import com.megacity.backend.domain.entity.Driver;
import com.megacity.backend.domain.response.APIResponse;
import com.megacity.backend.driver_management.service.DriverService;
import com.megacity.backend.util.ResponseUtil;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class DriverServiceImpl implements DriverService {

    @NonNull
    private final JdbcTemplate writeJdbcTemplate;

    @NonNull
    private final JdbcTemplate readJdbcTemplate;

    @NonNull
    private final ResponseUtil responseUtil;

    public DriverServiceImpl(@NonNull JdbcTemplate writeJdbcTemplate, @NonNull JdbcTemplate readJdbcTemplate, @NonNull ResponseUtil responseUtil) {
        this.writeJdbcTemplate = writeJdbcTemplate;
        this.readJdbcTemplate = readJdbcTemplate;
        this.responseUtil = responseUtil;
    }

    @Override
    public ResponseEntity<APIResponse> getAllDrivers() {
        try {
            List<Driver> driverList = readJdbcTemplate.query(
                    SqlQuery.SelectQuery.FETCH_ALL_DRIVERS,
                    (rs, rowNum) -> Driver.builder()
                            .driverRegistrationNumber(rs.getInt("driver_registration_number"))
                            .rootUserId(rs.getInt("root_user_id"))
                            .driverNIC(rs.getString("driver_nic"))
                            .phoneNumber(rs.getString("phone_number"))
                            .licenseNumber(rs.getString("license_number"))
                            .licenseExpiryDate(rs.getDate("license_expiry_date"))
                            .driverAddress(rs.getString("driver_address"))
                            .vehicleAssigned(rs.getString("vehicle_assigned"))
                            .driverStatus(rs.getString("driver_status"))
                            .emergencyContact(rs.getString("emergency_contact"))
                            .dateOfBirth(rs.getDate("date_of_birth"))
                            .dateOfJoining(rs.getDate("date_of_joining"))
                            .licenseImageFront(rs.getString("license_image_front"))
                            .licenseImageBack(rs.getString("license_image_back"))
                            .user_profile_pic(rs.getString("user_profile_pic"))
                            .build()
            );
            log.info("All drivers fetched successfully. Total: {}", driverList.size());
            return responseUtil.wrapSuccess(driverList, HttpStatus.OK);
        } catch (DataAccessException e) {
            log.error("SQL error occurred while fetching all drivers: {}", e.getMessage());
            return responseUtil.wrapError("Failed to fetch all drivers",
                    "Database error: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            log.error("Unexpected error occurred while fetching all drivers: ", e);
            return responseUtil.wrapError("Failed to fetch all drivers",
                    e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<APIResponse> getDriverById(String driverRegNo) {
        try {
            Driver driver = readJdbcTemplate.queryForObject(SqlQuery.SelectQuery.GET_DRIVER_BY_NIC, new Object[]{driverRegNo}, (rs, rowNum) ->
                    Driver.builder()
                            .driverRegistrationNumber(rs.getInt("driver_registration_number"))
                            .driverNIC(rs.getString("driver_nic"))
                            .phoneNumber(rs.getString("phone_number"))
                            .licenseNumber(rs.getString("license_number"))
                            .licenseExpiryDate(rs.getDate("license_expiry_date"))
                            .driverAddress(rs.getString("driver_address"))
                            .vehicleAssigned(rs.getString("vehicle_assigned"))
                            .driverStatus(rs.getString("driver_status"))
                            .emergencyContact(rs.getString("emergency_contact"))
                            .dateOfBirth(rs.getDate("date_of_birth"))
                            .dateOfJoining(rs.getDate("date_of_joining"))
                            .build());

            return responseUtil.wrapSuccess(driver, HttpStatus.OK);
        } catch (Exception e) {
            log.warn("Failed to retrieve driver {}", e.getMessage());
            return responseUtil.wrapError("Failed to retrieve driver", e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
