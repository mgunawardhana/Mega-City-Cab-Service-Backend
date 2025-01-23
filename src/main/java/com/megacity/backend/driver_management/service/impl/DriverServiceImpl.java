package com.megacity.backend.driver_management.service.impl;

import com.megacity.backend.constant.SqlQuery;
import com.megacity.backend.domain.entity.Driver;
import com.megacity.backend.domain.response.APIResponse;
import com.megacity.backend.driver_management.service.DriverService;
import com.megacity.backend.util.ResponseUtil;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

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
    public ResponseEntity<APIResponse> registerDriver(Driver driver) {
        try {
            writeJdbcTemplate.update(SqlQuery.InsertQuery.ADD_NEW_DRIVER, driver.getDriverFirstName(), driver.getDriverLastName(), driver.getDriverNIC(), driver.getPhoneNumber(), driver.getEmailAddress(), driver.getLicenseNumber(), driver.getLicenseExpiryDate(), driver.getDriverAddress(), driver.getVehicleAssigned(), driver.getDriverStatus(), driver.getEmergencyContact(), driver.getDateOfBirth(), driver.getDateOfJoining());
            log.info("Driver registered successfully");
            return responseUtil.wrapSuccess("Driver registered successfully", HttpStatus.OK);
        } catch (Exception e) {
            log.warn("Failed to register driver {}", e.getMessage());
            return responseUtil.wrapError("Failed to register driver", e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<APIResponse> getDriverById(String driverRegNo) {
        try {
            Driver driver = readJdbcTemplate.queryForObject(SqlQuery.SelectQuery.GET_DRIVER_BY_NIC, new Object[]{driverRegNo}, (rs, rowNum) -> Driver.builder().driverRegistrationNumber(rs.getInt("driver_registration_number")).driverFirstName(rs.getString("driver_first_name")).driverLastName(rs.getString("driver_last_name")).driverNIC(rs.getString("driver_nic")).phoneNumber(rs.getString("phone_number")).emailAddress(rs.getString("email_address")).licenseNumber(rs.getString("license_number")).licenseExpiryDate(rs.getDate("license_expiry_date")).driverAddress(rs.getString("driver_address")).vehicleAssigned(rs.getString("vehicle_assigned")).driverStatus(rs.getString("driver_status")).emergencyContact(rs.getString("emergency_contact")).dateOfBirth(rs.getDate("date_of_birth")).dateOfJoining(rs.getDate("date_of_joining")).build());

            return responseUtil.wrapSuccess(driver, HttpStatus.OK);
        } catch (Exception e) {
            log.warn("Failed to retrieve driver {}", e.getMessage());
            return responseUtil.wrapError("Failed to retrieve driver", e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<APIResponse> getAllDrivers() {
        try {
            List<Driver> driverList = readJdbcTemplate.query(SqlQuery.SelectQuery.FETCH_ALL_DRIVERS, (rs, rowNum) -> Driver.builder().driverRegistrationNumber(rs.getInt("driver_registration_number")).driverFirstName(rs.getString("driver_first_name")).driverLastName(rs.getString("driver_last_name")).driverNIC(rs.getString("driver_nic")).phoneNumber(rs.getString("phone_number")).emailAddress(rs.getString("email_address")).licenseNumber(rs.getString("license_number")).licenseExpiryDate(rs.getDate("license_expiry_date")).driverAddress(rs.getString("driver_address")).vehicleAssigned(rs.getString("vehicle_assigned")).driverStatus(rs.getString("driver_status")).emergencyContact(rs.getString("emergency_contact")).dateOfBirth(rs.getDate("date_of_birth")).dateOfJoining(rs.getDate("date_of_joining")).build());
            log.info("All drivers fetched successfully. Total: {}", driverList.size());
            return responseUtil.wrapSuccess(driverList, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error occurred while fetching all drivers: ", e);
            return responseUtil.wrapError("Failed to fetch all drivers", e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @Override
    public ResponseEntity<APIResponse> deleteDriverById(String licenseNumber) {
        try {
            writeJdbcTemplate.update(SqlQuery.DeleteQuery.DELETE_DRIVER_BY_NIC, licenseNumber);

            return responseUtil.wrapSuccess("Driver deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            log.warn("Failed to delete driver {}", e.getMessage());
            return responseUtil.wrapError("Failed to delete driver", e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<APIResponse> updateDriver(Driver driver) {
        try {
            writeJdbcTemplate.update(SqlQuery.UpdateQuery.UPDATE_DRIVER, driver.getDriverFirstName(), driver.getDriverLastName(), driver.getDriverNIC(), driver.getPhoneNumber(), driver.getEmailAddress(), driver.getLicenseNumber(), driver.getLicenseExpiryDate(), driver.getDriverAddress(), driver.getVehicleAssigned(), driver.getDriverStatus(), driver.getEmergencyContact(), driver.getDateOfBirth(), driver.getDateOfJoining(), driver.getDriverRegistrationNumber());
            return responseUtil.wrapSuccess("Driver updated successfully", HttpStatus.OK);
        } catch (Exception e) {
            log.warn("Failed to update driver: {}", e.getMessage());
            return responseUtil.wrapError("Failed to update driver", e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
