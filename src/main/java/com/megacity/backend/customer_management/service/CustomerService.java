package com.megacity.backend.customer_management.service;

import com.megacity.backend.domain.entity.Customer;
import com.megacity.backend.domain.response.APIResponse;
import org.springframework.http.ResponseEntity;

public interface CustomerService {

    /**
     * Retrieves all customers.
     *
     * @return a ResponseEntity containing the APIResponse with the list of customers
     */
    ResponseEntity<APIResponse> getAllCustomers();

    /**
     * Retrieves a customer by their ID.
     *
     * @param customerId the ID of the customer to be retrieved
     * @return a ResponseEntity containing the APIResponse with the customer details
     */
    ResponseEntity<APIResponse> getCustomerById(Integer customerId);

    /**
     * Creates a new customer.
     *
     * @param customer the customer to be created
     * @return a ResponseEntity containing the APIResponse
     */
    ResponseEntity<APIResponse> createCustomer(Customer customer);

    /**
     * Updates an existing customer.
     *
     * @param customer the customer to be updated
     * @return a ResponseEntity containing the APIResponse
     */
    ResponseEntity<APIResponse> updateCustomer(Customer customer);

    /**
     * Deletes a customer by their ID.
     *
     * @param customerId the ID of the customer to be deleted
     * @return a ResponseEntity containing the APIResponse
     */
    ResponseEntity<APIResponse> deleteCustomer(Integer customerId);

    /**
     * Retrieves a customer by their NIC.
     *
     * @param customerNIC the NIC of the customer to be retrieved
     * @return a ResponseEntity containing the APIResponse with the customer details
     */
    ResponseEntity<APIResponse> getCustomerByNIC(String customerNIC);
}
