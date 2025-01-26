package com.megacity.backend.report_management.controller;

import com.megacity.backend.domain.response.APIResponse;
import com.megacity.backend.report_management.service.ReportService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("api/v1/report")
@RequiredArgsConstructor
public class ReportController {

    @NonNull
    private final ReportService reportService;

    @GetMapping("/fetch-all")
    public ResponseEntity<APIResponse> getTotalIncome() {
        log.info("getTotalIncome start");
        var response = reportService.getTotalIncome();
        log.info("fetchAllReportRecords {}", response);
        return response;
    }

    @GetMapping("/expenses")
    public ResponseEntity<APIResponse> getTotalExpenses(@RequestParam String customerNIC) {
        log.info("getTotalExpenses start");
        var response = reportService.getTotalExpenses(customerNIC);
        log.info("getTotalExpenses {}", response);
        return response;
    }

    @GetMapping("/income/day")
    public ResponseEntity<APIResponse> getTotalIncomeDayWise(@RequestParam String selectedDate) {
        log.info("getTotalIncomeDayWise start");
        var response = reportService.getTotalIncomeDayWise(selectedDate);
        log.info("getTotalIncomeDayWise {}", response);
        return response;
    }

    @GetMapping("/income/month")
    public ResponseEntity<APIResponse> getTotalIncomeMonthWise(@RequestParam String selectedMonth) {
        log.info("getTotalIncomeMonthWise start");
        var response = reportService.getTotalIncomeMonthWise(selectedMonth);
        log.info("getTotalIncomeMonthWise {}", response);
        return response;
    }

    @GetMapping("/income/year")
    public ResponseEntity<APIResponse> getTotalIncomeAnnually(@RequestParam String selectedYear) {
        log.info("getTotalIncomeAnnually start");
        var response = reportService.getTotalIncomeAnnually(selectedYear);
        log.info("getTotalIncomeAnnually {}", response);
        return response;
    }

    @GetMapping("/customer")
    public ResponseEntity<APIResponse> getCustomerWiseBookings(@RequestParam String customerNIC) {
        log.info("getCustomerWiseBookings start");
        var response = reportService.getCustomerWiseBookings(customerNIC);
        log.info("getCustomerWiseBookings {}", response);
        return response;
    }

    @GetMapping("/driver")
    public ResponseEntity<APIResponse> getDriverWiseSalary(@RequestParam String driverNIC) {
        log.info("getDriverWiseSalary start");
        var response = reportService.getDriverWiseSalary(driverNIC);
        log.info("getDriverWiseSalary {}", response);
        return response;
    }

    @GetMapping("/manager")
    public ResponseEntity<APIResponse> getManagerWiseSalary(@RequestParam String managerNIC) {
        log.info("getManagerWiseSalary start");
        var response = reportService.getManagerWiseSalary(managerNIC);
        log.info("getManagerWiseSalary {}", response);
        return response;
    }

    @GetMapping("/vehicle/income")
    public ResponseEntity<APIResponse> getVehicleWiseIncome(@RequestParam String vehicleNumber) {
        log.info("getVehicleWiseIncome start");
        var response = reportService.getVehicleWiseIncome(vehicleNumber);
        log.info("getVehicleWiseIncome {}", response);
        return response;
    }

    @GetMapping("/vehicle/fuel")
    public ResponseEntity<APIResponse> getVehicleWiseFuelConsumptionWisExpenses(@RequestParam String vehicleNumber) {
        log.info("getVehicleWiseFuelConsumptionWisExpenses start");
        var response = reportService.getVehicleWiseFuelConsumptionWisExpenses(vehicleNumber);
        log.info("getVehicleWiseFuelConsumptionWisExpenses {}", response);
        return response;
    }

}
