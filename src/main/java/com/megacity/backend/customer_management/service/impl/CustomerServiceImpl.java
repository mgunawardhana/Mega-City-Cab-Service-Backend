package com.megacity.backend.customer_management.service.impl;

import com.megacity.backend.customer_management.service.CustomerService;
import com.megacity.backend.domain.entity.Customer;
import com.megacity.backend.domain.response.APIResponse;
import com.megacity.backend.util.ResponseUtil;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {

    @NonNull
    private final JdbcTemplate writeJdbcTemplate;

    @NonNull
    private final JdbcTemplate readJdbcTemplate;

    @NonNull
    private final ResponseUtil responseUtil;

    public CustomerServiceImpl(@NonNull JdbcTemplate writeJdbcTemplate, @NonNull JdbcTemplate readJdbcTemplate, @NonNull ResponseUtil responseUtil) {
        this.writeJdbcTemplate = writeJdbcTemplate;
        this.readJdbcTemplate = readJdbcTemplate;
        this.responseUtil = responseUtil;
    }

    @Override
    public ResponseEntity<APIResponse> getAllCustomers() {
        return null;
    }

    @Override
    public ResponseEntity<APIResponse> getCustomerById(Integer customerId) {
        return null;
    }

    @Override
    public ResponseEntity<APIResponse> createCustomer(Customer customer) {
        return null;
    }

    @Override
    public ResponseEntity<APIResponse> updateCustomer(Customer customer) {
        return null;
    }

    @Override
    public ResponseEntity<APIResponse> deleteCustomer(Integer customerId) {
        return null;
    }

    @Override
    public ResponseEntity<APIResponse> getCustomerByNIC(String customerNIC) {
        return null;
    }
}
