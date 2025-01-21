package com.megacity.backend.customer_management.service;

import com.megacity.backend.domain.entity.Customer;
import com.megacity.backend.domain.entity.User;
import com.megacity.backend.domain.response.APIResponse;
import org.springframework.http.ResponseEntity;

public interface CustomerService {

    ResponseEntity<APIResponse> updateCustomer(User user);
    ResponseEntity<APIResponse> deleteCustomer(Integer userId);
    ResponseEntity<APIResponse> getCustomer(Integer userId);
    ResponseEntity<APIResponse> getAllCustomers();
}
