package com.megacity.backend.service;

import com.megacity.backend.domain.entity.Customer;
import com.megacity.backend.domain.response.APIResponse;
import com.megacity.backend.util.ResponseUtil;
import com.megacity.backend.customer_management.service.impl.CustomerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomerManagementServiceTest {

    private JdbcTemplate writeJdbcTemplate;
    private JdbcTemplate readJdbcTemplate;
    private ResponseUtil responseUtil;
    private CustomerServiceImpl customerService;

    @BeforeEach
    void setUp() {
        writeJdbcTemplate = mock(JdbcTemplate.class);
        readJdbcTemplate = mock(JdbcTemplate.class);
        responseUtil = mock(ResponseUtil.class);
        customerService = new CustomerServiceImpl(writeJdbcTemplate, readJdbcTemplate, responseUtil);
    }

    private Customer createSampleCustomer() {
        return Customer.builder()
                .registrationNumber(1)
                .rootUserId(100)
                .address("123 Main St")
                .NIC("NIC123456")
                .phoneNumber("555-123-4567")
                .build();
    }

    @Test
    @DisplayName("Get all customers - Success scenario")
    void getAllCustomersSuccessTest() {
        List<Customer> customers = Collections.singletonList(createSampleCustomer());
        when(readJdbcTemplate.query(anyString(), any(RowMapper.class))).thenReturn(customers);
        when(responseUtil.wrapSuccess(customers, HttpStatus.OK))
                .thenReturn(new ResponseEntity<>(APIResponse.builder().result(customers).build(), HttpStatus.OK));
        ResponseEntity<APIResponse> response = customerService.getAllCustomers();
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(customers, response.getBody().getResult());
        verify(readJdbcTemplate, times(1)).query(anyString(), any(RowMapper.class));
    }

    @Test
    @DisplayName("Get all customers - Error scenario")
    void getAllCustomersErrorTest() {
        RuntimeException exception = new RuntimeException("DB error");
        when(readJdbcTemplate.query(anyString(), any(RowMapper.class))).thenThrow(exception);
        when(responseUtil.wrapError("Error fetching customers", exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR))
                .thenReturn(new ResponseEntity<>(APIResponse.builder()
                        .statusMessage("Error fetching customers")
                        .build(), HttpStatus.INTERNAL_SERVER_ERROR));
        ResponseEntity<APIResponse> response = customerService.getAllCustomers();
        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Error fetching customers", response.getBody().getStatusMessage());
    }

    @Test
    @DisplayName("Get customer by ID - Success scenario")
    void getCustomerByIdSuccessTest() {
        Customer customer = createSampleCustomer();
        when(readJdbcTemplate.queryForObject(anyString(), eq(new Object[]{1}), any(RowMapper.class))).thenReturn(customer);
        when(responseUtil.wrapSuccess(customer, HttpStatus.OK))
                .thenReturn(new ResponseEntity<>(APIResponse.builder().result(customer).build(), HttpStatus.OK));
        ResponseEntity<APIResponse> response = customerService.getCustomerById(1);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(customer, response.getBody().getResult());
    }

    @Test
    @DisplayName("Create customer - Success scenario")
    void createCustomerSuccessTest() {
        Customer customer = createSampleCustomer();
        when(writeJdbcTemplate.update(anyString(), anyInt(), anyString(), anyString(), anyString())).thenReturn(1);
        when(responseUtil.wrapSuccess("Customer created successfully", HttpStatus.OK))
                .thenReturn(new ResponseEntity<>(APIResponse.builder()
                        .statusMessage("Customer created successfully")
                        .build(), HttpStatus.OK));
        ResponseEntity<APIResponse> response = customerService.createCustomer(customer);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Customer created successfully", response.getBody().getStatusMessage());
    }

    @Test
    @DisplayName("Create customer - Error scenario")
    void createCustomerErrorTest() {
        Customer customer = createSampleCustomer();
        RuntimeException exception = new RuntimeException("DB error");
        when(writeJdbcTemplate.update(anyString(), anyInt(), anyString(), anyString(), anyString())).thenThrow(exception);
        when(responseUtil.wrapError("Error creating customer", exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR))
                .thenReturn(new ResponseEntity<>(APIResponse.builder()
                        .statusMessage("Error creating customer")
                        .build(), HttpStatus.INTERNAL_SERVER_ERROR));
        ResponseEntity<APIResponse> response = customerService.createCustomer(customer);
        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Error creating customer", response.getBody().getStatusMessage());
    }

    @Test
    @DisplayName("Update customer - Success scenario")
    void updateCustomerSuccessTest() {
        Customer customer = createSampleCustomer();
        when(writeJdbcTemplate.update(anyString(), anyInt(), anyString(), anyString(), anyString(), anyInt())).thenReturn(1);
        when(responseUtil.wrapSuccess("Customer updated successfully", HttpStatus.OK))
                .thenReturn(new ResponseEntity<>(APIResponse.builder()
                        .statusMessage("Customer updated successfully")
                        .build(), HttpStatus.OK));
        ResponseEntity<APIResponse> response = customerService.updateCustomer(customer);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Customer updated successfully", response.getBody().getStatusMessage());
    }

    @Test
    @DisplayName("Delete customer - Success scenario")
    void deleteCustomerSuccessTest() {
        when(writeJdbcTemplate.update(anyString(), eq(1))).thenReturn(1);
        when(responseUtil.wrapSuccess("Customer deleted successfully", HttpStatus.OK))
                .thenReturn(new ResponseEntity<>(APIResponse.builder()
                        .statusMessage("Customer deleted successfully")
                        .build(), HttpStatus.OK));
        ResponseEntity<APIResponse> response = customerService.deleteCustomer(1);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Customer deleted successfully", response.getBody().getStatusMessage());
    }

    @Test
    @DisplayName("Get customer by NIC - Success scenario")
    void getCustomerByNicSuccessTest() {
        Customer customer = createSampleCustomer();
        when(readJdbcTemplate.queryForObject(anyString(), eq(new Object[]{"NIC123456"}), any(RowMapper.class))).thenReturn(customer);
        when(responseUtil.wrapSuccess(customer, HttpStatus.OK))
                .thenReturn(new ResponseEntity<>(APIResponse.builder().result(customer).build(), HttpStatus.OK));
        ResponseEntity<APIResponse> response = customerService.getCustomerByNIC("NIC123456");
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(customer, response.getBody().getResult());
    }

    @Test
    @DisplayName("Get customer by NIC - Error scenario")
    void getCustomerByNicErrorTest() {
        RuntimeException exception = new RuntimeException("DB error");
        when(readJdbcTemplate.queryForObject(anyString(), eq(new Object[]{"NIC123456"}), any(RowMapper.class))).thenThrow(exception);
        when(responseUtil.wrapError("Error fetching customer", exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR))
                .thenReturn(new ResponseEntity<>(APIResponse.builder()
                        .statusMessage("Error fetching customer")
                        .build(), HttpStatus.INTERNAL_SERVER_ERROR));
        ResponseEntity<APIResponse> response = customerService.getCustomerByNIC("NIC123456");
        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Error fetching customer", response.getBody().getStatusMessage());
    }
}