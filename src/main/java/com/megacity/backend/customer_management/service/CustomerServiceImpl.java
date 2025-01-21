package com.megacity.backend.customer_management.service;

import com.megacity.backend.authentication.repository.UserRepository;
import com.megacity.backend.customer_management.repository.CustomerRepository;
import com.megacity.backend.domain.entity.Customer;
import com.megacity.backend.domain.entity.Driver;
import com.megacity.backend.domain.entity.User;
import com.megacity.backend.domain.response.APIResponse;
import com.megacity.backend.driver_management.repository.DriverRepository;
import com.megacity.backend.util.ResponseUtil;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private final CustomerRepository customerRepository;

    @Autowired
    private final DriverRepository driverRepository;

    @Autowired
    private final UserRepository userRepository;

    @NonNull
    private final ResponseUtil responseUtil;


    @Override
    public ResponseEntity<APIResponse> updateCustomer(User user) {
        try {
            Optional<User> existingUserOptional = userRepository.findById(user.getId());
            if (existingUserOptional.isPresent()) {
                User existingUser = existingUserOptional.get();

                existingUser.setFirstName(user.getFirstName());
                existingUser.setLastName(user.getLastName());
                existingUser.setEmail(user.getEmail());
                existingUser.setPassword(user.getPassword());
                existingUser.setRole(user.getRole());

                Customer existingCustomer = existingUser.getCustomer();
                if (existingCustomer == null) {
                    existingCustomer = new Customer();
                    existingCustomer.setUser(existingUser);
                    existingUser.setCustomer(existingCustomer);
                } else {
                    existingCustomer.setAddress(user.getCustomer().getAddress());
                    existingCustomer.setPhoneNumber(user.getCustomer().getPhoneNumber());
                }

                Driver existingDriver = existingUser.getDriver();
                if (existingDriver == null) {
                    existingDriver = new Driver();
                    existingDriver.setUser(existingUser);
                    existingUser.setDriver(existingDriver);
                } else {
                    existingDriver.setLicenseNumber(user.getDriver().getLicenseNumber());
                    existingDriver.setVehicleDetails(user.getDriver().getVehicleDetails());
                }

                userRepository.save(existingUser);
                customerRepository.save(existingCustomer);
                driverRepository.save(existingDriver);

                return responseUtil.wrapSuccess(existingUser.getId() + " Customer updated successfully", HttpStatus.OK);
            } else {
                return responseUtil.wrapError("User not found", "User with ID " + user.getId() + " does not exist", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            log.error("Error occurred while updating customer: ", e);
            return responseUtil.wrapError("Error occurred while updating customer", e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<APIResponse> deleteCustomer() {
        return null;
    }

    @Override
    public ResponseEntity<APIResponse> getCustomer() {
        return null;
    }

    @Override
    public ResponseEntity<APIResponse> getAllCustomers() {
        return null;
    }
}
