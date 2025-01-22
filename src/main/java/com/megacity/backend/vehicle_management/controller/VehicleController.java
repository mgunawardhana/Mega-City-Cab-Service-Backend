package com.megacity.backend.vehicle_management.controller;


import com.megacity.backend.domain.entity.Vehicle;
import com.megacity.backend.domain.response.APIResponse;
import com.megacity.backend.vehicle_management.service.VehicleService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("api/v1/vehicle")
@RequiredArgsConstructor
public class VehicleController {

    @NonNull
    private final VehicleService vehicleService;

    @PostMapping("/register")
    public ResponseEntity<APIResponse> registerVehicle(@RequestBody Vehicle vehicle){
        log.info("registerVehicle {}",vehicle);
        var response = vehicleService.registerVehicle(vehicle);
        log.info("registerVehicle {}",response);
        return response;
    }

    @GetMapping("/{id}")
    public ResponseEntity<APIResponse> fetchVehicleById(@PathVariable String id){
        log.info("fetchVehicleById {}",id);
        var response = vehicleService.fetchVehicleById(Long.valueOf(id));
        log.info("fetchVehicleById {}",response);
        return response;
    }

    @GetMapping("/fetch-all")
    public ResponseEntity<APIResponse> fetchAllVehicleRecords(){
        log.info("fetchAllVehicleRecords start");
        var response = vehicleService.fetchAllVehicle();
        log.info("fetchAllVehicleRecords {}",response);
        return response;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse> deleteVehicle(@PathVariable Long id){
        log.info("deleteVehicle {}",id);
        var response = vehicleService.deleteVehicle(id);
        log.info("deleteVehicle {}",response);
        return response;
    }

    @PutMapping("/update")
    public ResponseEntity<APIResponse> updateVehicle(@RequestBody Vehicle vehicle){
        log.info("updateVehicle {}",vehicle);
        var response = vehicleService.updateVehicle(vehicle);
        log.info("updateVehicle {}",response);
        return response;
    }

}
