package com.megacity.backend.report_management.service.impl;

import com.megacity.backend.domain.response.APIResponse;
import com.megacity.backend.report_management.service.ReportService;
import com.megacity.backend.util.ResponseUtil;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ReportServiceImpl implements ReportService {

    @NonNull
    private final JdbcTemplate writeJdbcTemplate;

    @NonNull
    private final JdbcTemplate readJdbcTemplate;

    @NonNull
    private final ResponseUtil responseUtil;

    public ReportServiceImpl(@NonNull JdbcTemplate writeJdbcTemplate, @NonNull JdbcTemplate readJdbcTemplate, @NonNull ResponseUtil responseUtil) {
        this.writeJdbcTemplate = writeJdbcTemplate;
        this.readJdbcTemplate = readJdbcTemplate;
        this.responseUtil = responseUtil;
    }

    @Override
    public ResponseEntity<APIResponse> getTotalIncomeDayWise(String selectedDate) {
        return null;
    }

    @Override
    public ResponseEntity<APIResponse> getTotalIncomeMonthWise(String SelectedMonth) {
        return null;
    }

    @Override
    public ResponseEntity<APIResponse> getTotalIncomeAnnually(String SelectedYear) {
        return null;
    }

    @Override
    public ResponseEntity<APIResponse> getCustomerWiseBookings(String customerNIC) {
        return null;
    }

    @Override
    public ResponseEntity<APIResponse> getDriverWiseSalary(String driverNIC) {
        return null;
    }

    @Override
    public ResponseEntity<APIResponse> getManagerWiseSalary(String managerNIC) {
        return null;
    }

    @Override
    public ResponseEntity<APIResponse> getVehicleWiseIncome(String vehicleNumber) {
        return null;
    }

    @Override
    public ResponseEntity<APIResponse> getVehicleWiseFuelConsumptionWisExpenses(String vehicleNumber) {
        return null;
    }

    @Override
    public ResponseEntity<APIResponse> getTotalIncome() {
        return null;
    }

    @Override
    public ResponseEntity<APIResponse> getTotalExpenses(String customerNIC) {
        return null;
    }
}
