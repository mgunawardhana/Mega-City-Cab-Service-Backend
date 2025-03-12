package com.megacity.backend.driver_management.controller;


import com.megacity.backend.domain.response.APIResponse;
import com.megacity.backend.driver_management.service.DriverService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("api/v1/driver")
@RequiredArgsConstructor
public class DriverController {

    @NonNull
    private final DriverService driverService;

    @GetMapping("/{id}")
    public ResponseEntity<APIResponse> fetchDriverById(@PathVariable String id) {
        log.info("fetchDriverById {}", id);
        var response = driverService.getDriverById(id);
        log.info("fetchDriverById {}", response);
        return response;
    }

    @GetMapping("/fetch-all")
    public ResponseEntity<APIResponse> fetchAllDriverRecords() {
        log.info("fetchAllDriverRecords start");
        var response = driverService.getAllDrivers();
        log.info("fetchAllDriverRecords {}", response);
        return response;
    }

    @PutMapping("/update-availability/{id}/{availability}")
    public ResponseEntity<APIResponse> updateAvailability(@PathVariable Integer id, @PathVariable String availability) {
        log.error("updateAvailability {} {}", id, availability);
        var response = driverService.updateDriverStatus(id, availability);
        log.info("updateAvailability {} {}", id, availability);
        return response;
    }
}
