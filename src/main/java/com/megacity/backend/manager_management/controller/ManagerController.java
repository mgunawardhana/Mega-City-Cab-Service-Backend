package com.megacity.backend.manager_management.controller;


import com.megacity.backend.domain.entity.Manager;
import com.megacity.backend.domain.response.APIResponse;
import com.megacity.backend.manager_management.service.ManagerService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("api/v1/manager")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class ManagerController {


    @NonNull
    private final ManagerService managerService;

    @PutMapping("/update")
    public ResponseEntity<APIResponse> updateManager(@RequestBody Manager manager) {
        log.info("updateManager {}", manager);
        var response = managerService.UpdateManager(manager);
        log.info("updateManager {}", response);
        return response;
    }

    @GetMapping("/{managerId}")
    public ResponseEntity<APIResponse> getManagerById(@PathVariable Integer managerId) {
        log.info("getManagerById {}", managerId);
        var response = managerService.getManagerById(managerId);
        log.info("getManagerById {}", response);
        return response;
    }

    @GetMapping("/all")
    public ResponseEntity<APIResponse> getAllManagers() {
        log.info("getAllManagers");
        var response = managerService.getAllManagers();
        log.info("getAllManagers {}", response);
        return response;
    }

    @PostMapping("/create")
    public ResponseEntity<APIResponse> createManager(@RequestBody Manager manager) {
        log.info("createManager {}", manager);
        var response = managerService.createManager(manager);
        log.info("createManager {}", response);
        return response;
    }

    @DeleteMapping("/delete/{managerId}")
    public ResponseEntity<APIResponse> deleteManager(@PathVariable Integer managerId) {
        log.info("deleteManager {}", managerId);
        var response = managerService.deleteManager(managerId);
        log.info("deleteManager {}", response);
        return response;
    }
}
