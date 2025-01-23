package com.megacity.backend.manager_management.controller;


import com.megacity.backend.domain.entity.Manager;
import com.megacity.backend.domain.response.APIResponse;
import com.megacity.backend.manager_management.service.ManagerService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("api/v1/manager")
@RequiredArgsConstructor
public class ManagerController {

    //TODO must need to check
    @NonNull
    private final ManagerService managerService;

    @PutMapping("/update")
    public ResponseEntity<APIResponse> updateManager(@RequestBody Manager manager) {
        log.info("updateManager {}", manager);
        var response = managerService.UpdateManager(manager);
        log.info("updateManager {}", response);
        return response;
    }

    @GetMapping("/{id}")
    public ResponseEntity<APIResponse> getManagerById(@PathVariable String id) {
        log.info("getManagerById {}", id);
        var response = managerService.getManagerById(Integer.valueOf(id));
        log.info("getManagerById {}", response);
        return response;
    }

    @GetMapping("/fetch-all")
    public ResponseEntity<APIResponse> fetchAllManagerRecords() {
        log.info("fetchAllManagerRecords start");
        var response = managerService.getAllManagers();
        log.info("fetchAllManagerRecords {}", response);
        return response;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse> deleteManager(@PathVariable String id) {
        log.info("deleteManager {}", id);
        var response = managerService.deleteManager(Integer.valueOf(id));
        log.info("deleteManager {}", response);
        return response;
    }

    @PostMapping("/register")
    public ResponseEntity<APIResponse> registerManager(@RequestBody Manager manager) {
        log.info("registerManager {}", manager);
        var response = managerService.createManager(manager);
        log.info("registerManager {}", response);
        return response;
    }


}
