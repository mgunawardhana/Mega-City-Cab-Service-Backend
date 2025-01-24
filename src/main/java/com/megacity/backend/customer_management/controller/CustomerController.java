package com.megacity.backend.customer_management.controller;


import com.megacity.backend.customer_management.service.CustomerService;
import com.megacity.backend.domain.entity.Customer;
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
public class CustomerController {

    @NonNull
    private final CustomerService customerService;


    @GetMapping("/all")
    public ResponseEntity<APIResponse> getAllCustomers() {
        log.info("getAllCustomers start");
        var response = customerService.getAllCustomers();
        log.info("getAllCustomers {}", response);
        return response;
    }

    @GetMapping("/{id}")
    public ResponseEntity<APIResponse> getCustomerById(@PathVariable String id) {
        log.info("getCustomerById {}", id);
        var response = customerService.getCustomerById(Integer.valueOf(id));
        log.info("getCustomerById {}", response);
        return response;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse> deleteCustomer(@PathVariable String id) {
        log.info("deleteCustomer {}", id);
        var response = customerService.deleteCustomer(Integer.valueOf(id));
        log.info("deleteCustomer {}", response);
        return response;
    }

    @PutMapping("/update")
    public ResponseEntity<APIResponse> updateCustomer(@RequestBody Customer customer) {
        log.info("updateCustomer {}", customer);
        var response = customerService.updateCustomer(customer);
        log.info("updateCustomer {}", response);
        return response;
    }

    @PostMapping("/register")
    public ResponseEntity<APIResponse> registerCustomer(@RequestBody Customer customer) {
        log.info("registerCustomer {}", customer);
        var response = customerService.createCustomer(customer);
        log.info("registerCustomer {}", response);
        return response;
    }

}
