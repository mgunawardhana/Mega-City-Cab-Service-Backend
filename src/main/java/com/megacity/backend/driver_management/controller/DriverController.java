package com.megacity.backend.driver_management.controller;


import com.megacity.backend.domain.entity.Driver;
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

    @NonNull private final DriverService driverService;

    @PostMapping("/register")
    public ResponseEntity<APIResponse> registerDriver(@RequestBody Driver driver){
        log.info("registerDriver {}",driver);
        var response = driverService.registerDriver(driver);
        log.info("registerDriver {}",response);
        return response;
    }

    @GetMapping("/{id}")
    public ResponseEntity<APIResponse> fetchDriverById(@PathVariable String id){
        log.info("fetchDriverById {}",id);
        var response = driverService.getDriverById(id);
        log.info("fetchDriverById {}",response);
        return response;
    }

    @GetMapping("/fetch-all")
    public ResponseEntity<APIResponse> fetchAllDriverRecords(){
        log.info("fetchAllDriverRecords start");
        var response = driverService.getAllDrivers();
        log.info("fetchAllDriverRecords {}",response);
        return response;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse> deleteDriver(@PathVariable String id){
        log.info("deleteDriver {}",id);
        var response = driverService.deleteDriverById(id);
        log.info("deleteDriver {}",response);
        return response;
    }

    @PutMapping("/update")
    public ResponseEntity<APIResponse> updateDriver(@RequestBody Driver driver){
        log.info("updateDriver {}",driver);
        var response = driverService.updateDriver(driver);
        log.info("updateDriver {}",response);
        return response;
    }
}
