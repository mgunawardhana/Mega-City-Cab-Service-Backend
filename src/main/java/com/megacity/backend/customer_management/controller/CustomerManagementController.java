package com.megacity.backend.customer_management.controller;

import com.megacity.backend.customer_management.service.CustomerServiceImpl;
import com.megacity.backend.domain.entity.User;
import com.megacity.backend.domain.response.APIResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("api/v1/customer")
@RequiredArgsConstructor
public class CustomerManagementController {

    @NonNull
    private final CustomerServiceImpl customerManagementServiceImpl;

    @PutMapping
    public ResponseEntity<APIResponse> updateCustomer(@RequestBody User user) {
        log.info("logging request object : {}", user);
        var response = customerManagementServiceImpl.updateCustomer(user);
        log.info("logging response object : {}", response);
        return response;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse> deleteCustomer(@PathVariable String id) {
        log.info("logging request object from delete : {}", id);
        var response = customerManagementServiceImpl.deleteCustomer(Integer.valueOf(id));
        log.info("logging response object from delete : {}", response);
        return response;
    }

}
