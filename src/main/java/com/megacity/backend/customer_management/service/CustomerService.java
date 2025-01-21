package com.megacity.backend.customer_management.service;

import com.megacity.backend.domain.entity.Customer;
import com.megacity.backend.domain.entity.User;
import com.megacity.backend.domain.response.APIResponse;
import org.springframework.http.ResponseEntity;

public interface CustomerService {

    /**
     * Updates the customer information associated with the given user.
     *
     * @param user the user whose customer information is to be updated
     * @return a ResponseEntity containing the API response
     */
    ResponseEntity<APIResponse> updateCustomer(User user);

    /**
     * Deletes the customer information associated with the given user ID.
     *
     * @param userId the ID of the user whose customer information is to be deleted
     * @return a ResponseEntity containing the API response
     */
    ResponseEntity<APIResponse> deleteCustomer(Integer userId);

    /**
     * Retrieves the customer information associated with the given user ID.
     *
     * @param userId the ID of the user whose customer information is to be retrieved
     * @return a ResponseEntity containing the API response
     */
    ResponseEntity<APIResponse> getCustomer(Integer userId);

    /**
     * Retrieves all customers.
     *
     * @return a ResponseEntity containing the API response with a list of all customers
     */
    ResponseEntity<APIResponse> getAllCustomers();
}
