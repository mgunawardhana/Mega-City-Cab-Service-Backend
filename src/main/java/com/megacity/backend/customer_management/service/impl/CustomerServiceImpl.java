package com.megacity.backend.customer_management.service.impl;

import com.megacity.backend.constant.SqlQuery;
import com.megacity.backend.customer_management.service.CustomerService;
import com.megacity.backend.domain.entity.Customer;
import com.megacity.backend.domain.response.APIResponse;
import com.megacity.backend.util.ResponseUtil;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

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
        try {
            List<Customer> customers = readJdbcTemplate.query(SqlQuery.SelectQuery.GET_ALL_CUSTOMERS,
                    (rs, rowNum) -> Customer.builder()
                            .registrationNumber(rs.getInt("registration_number"))
                            .rootUserId(rs.getInt("root_user_id"))
                            .address(rs.getString("address"))
                            .NIC(rs.getString("nic"))
                            .phoneNumber(rs.getString("phone_number")).build());
            log.info("Fetched all customers successfully");
            return responseUtil.wrapSuccess(customers, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching customers", e);
            return responseUtil.wrapError("Error fetching customers", e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<APIResponse> getCustomerById(Integer customerId) {
        try {
            Customer customer = readJdbcTemplate.queryForObject(SqlQuery.SelectQuery.GET_CUSTOMER_BY_ID,
                    new Object[]{customerId}, (rs, rowNum) ->
                            Customer.builder().registrationNumber(
                                    rs.getInt("registration_number"))
                                    .rootUserId(rs.getInt("root_user_id"))
                                    .address(rs.getString("address"))
                                    .NIC(rs.getString("nic"))
                                    .phoneNumber(rs.getString("phone_number"))
                                    .build());
            log.info("Fetched customer successfully");
            return responseUtil.wrapSuccess(customer, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching customer", e);
            return responseUtil.wrapError("Error fetching customer", e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<APIResponse> createCustomer(Customer customer) {
        try{
            writeJdbcTemplate.update(SqlQuery.InsertQuery.ADD_NEW_CUSTOMER,
                    customer.getRootUserId(),
                    customer.getAddress(),
                    customer.getNIC(),
                    customer.getPhoneNumber());
            log.info("Customer created successfully");
            return responseUtil.wrapSuccess("Customer created successfully", HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error creating customer", e);
            return responseUtil.wrapError("Error creating customer", e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<APIResponse> updateCustomer(Customer customer) {
        try{
            writeJdbcTemplate.update(SqlQuery.UpdateQuery.UPDATE_CUSTOMER, customer.getRootUserId(), customer.getAddress(), customer.getNIC(), customer.getPhoneNumber(), customer.getRegistrationNumber());
            log.info("Customer updated successfully");
            return responseUtil.wrapSuccess("Customer updated successfully", HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error updating customer", e);
            return responseUtil.wrapError("Error updating customer", e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<APIResponse> deleteCustomer(Integer customerId) {
        try{
            writeJdbcTemplate.update(SqlQuery.DeleteQuery.DELETE_CUSTOMER_BY_ID, customerId);
            log.info("Customer deleted successfully");
            return responseUtil.wrapSuccess("Customer deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error deleting customer", e);
            return responseUtil.wrapError("Error deleting customer", e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<APIResponse> getCustomerByNIC(String customerNIC) {
        try {
            Customer customer = readJdbcTemplate.queryForObject(SqlQuery.SelectQuery.GET_CUSTOMER_BY_NIC,
                    new Object[]{customerNIC}, (rs, rowNum) ->
                            Customer.builder()
                                    .registrationNumber(rs.getInt("registration_number"))
                                    .rootUserId(rs.getInt("root_user_id"))
                                    .address(rs.getString("address"))
                                    .NIC(rs.getString("nic"))
                                    .phoneNumber(rs.getString("phone_number"))
                                    .build());
            log.info("Fetched customer by NIC successfully");
            return responseUtil.wrapSuccess(customer, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching customer", e);
            return responseUtil.wrapError("Error fetching customer", e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
