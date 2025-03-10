package com.megacity.backend.authentication.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.megacity.backend.authentication.repository.UserRepository;
import com.megacity.backend.authentication.service.impl.AuthenticationService;
import com.megacity.backend.constant.SqlQuery;
import com.megacity.backend.domain.entity.Customer;
import com.megacity.backend.domain.entity.Driver;
import com.megacity.backend.domain.entity.Manager;
import com.megacity.backend.domain.entity.User;
import com.megacity.backend.domain.enums.Role;
import com.megacity.backend.domain.enums.TokenType;
import com.megacity.backend.domain.request.AuthenticationRequest;
import com.megacity.backend.domain.request.RegistrationRequest;
import com.megacity.backend.domain.response.APIResponse;
import com.megacity.backend.domain.response.AuthenticationResponse;
import com.megacity.backend.util.ResponseUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    @NonNull
    private final UserRepository userRepository;

    @NonNull
    private final JdbcTemplate writeJdbcTemplate;

    @NonNull
    private final JdbcTemplate readJdbcTemplate;

    @NonNull
    private final PasswordEncoder passwordEncoder;

    @NonNull
    private final JwtServiceImpl jwtServiceImpl;

    @NonNull
    private final AuthenticationManager authenticationManager;

    @NonNull
    private final ResponseUtil responseUtil;

    @Override
    public ResponseEntity<APIResponse> findDriverEmailByDriverId(String email) throws IOException {
        try {
            Integer driverId = readJdbcTemplate.queryForObject(
                    SqlQuery.SelectQuery.FIND_ID_BY_EMAIL,
                    new Object[]{email},
                    (rs, rowNum) -> rs.getInt("id")
            );
            return responseUtil.wrapSuccess(driverId, HttpStatus.OK);
        } catch (Exception e) {
            log.warn("Failed to retrieve driver ID {}", e.getMessage());
            return responseUtil.wrapError("Failed to retrieve driver ID", e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Transactional
    public AuthenticationResponse register(RegistrationRequest registrationRequest) {

        try {
            var user = User.builder()
                    .firstName(registrationRequest.getFirstName())
                    .lastName(registrationRequest.getLastName())
                    .email(registrationRequest.getEmail())
                    .password(passwordEncoder.encode(registrationRequest.getPassword()))
                    .userProfilePic(registrationRequest.getDriverProfilePicture())
                    .role(registrationRequest.getRole())
                    .build();

            log.info("Processing registration for user: {}", user.getEmail());

            User savedUser = userRepository.save(user);

            try {
                if (savedUser.getRole().equals(Role.USER)) {
                    writeJdbcTemplate.update(SqlQuery.InsertQuery.ADD_NEW_CUSTOMER, savedUser.getId(), registrationRequest.getAddress(), registrationRequest.getNic(), registrationRequest.getPhone_number());
                    log.info("New Customer profile created successfully");
                } else if (savedUser.getRole().equals(Role.ADMIN)) {
                    writeJdbcTemplate.update(SqlQuery.InsertQuery.ADD_NEW_MANAGER, savedUser.getId(), registrationRequest.getAddress(), registrationRequest.getNic(), registrationRequest.getPhone_number());
                    log.info("New Manager profile created successfully");
                } else if (savedUser.getRole().equals(Role.DRIVER)) {
                    writeJdbcTemplate.update(SqlQuery.InsertQuery.ADD_NEW_DRIVER, savedUser.getId(), registrationRequest.getNic(), registrationRequest.getPhone_number(), registrationRequest.getLicenseNumber(), registrationRequest.getLicenseExpiryDate(), registrationRequest.getDriverAddress(), registrationRequest.getVehicleAssigned() != null ? registrationRequest.getVehicleAssigned() : "FALSE", registrationRequest.getDriverStatus() != null ? registrationRequest.getDriverStatus() : "Active", registrationRequest.getEmergencyContact(), registrationRequest.getDateOfBirth(), registrationRequest.getDateOfJoining(), registrationRequest.getLicenseImageFront(), registrationRequest.getLicenseImageBack());

                    log.info("## {}", registrationRequest.getLicenseImageFront());
                    log.info("## {}", registrationRequest.getLicenseImageBack());


                    log.info("New Driver profile created successfully");
                }
            } catch (Exception e) {
                log.error("Error creating profile for user {}: {}", savedUser.getEmail(), e.getMessage());
                throw new RuntimeException("Failed to create user profile", e);
            }

            String accessToken = jwtServiceImpl.generateToken(savedUser);
            String refreshToken = jwtServiceImpl.generateRefreshToken(savedUser);

            try {
                writeJdbcTemplate.update(SqlQuery.InsertQuery.INSERT_TOKEN, accessToken, TokenType.BEARER.name(), Boolean.FALSE, Boolean.FALSE, savedUser.getId());
                log.info("Token saved successfully for user: {}", savedUser.getEmail());
            } catch (Exception e) {
                log.error("Error saving token for user {}: {}", savedUser.getEmail(), e.getMessage());
                throw new RuntimeException("Failed to save authentication token", e);
            }

            return AuthenticationResponse.builder().accessToken(accessToken).refreshToken(refreshToken).build();

        } catch (Exception e) {
            log.error("Registration failed: {}", e.getMessage());
            throw new RuntimeException("Registration failed", e);
        }
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        var user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new RuntimeException("User not found"));

        log.info("AuthenticationResponse From Authenticate Function: {}", user);

        var accessToken = jwtServiceImpl.generateToken(user);
        var refreshToken = jwtServiceImpl.generateRefreshToken(user);

        try {
            int updatedRows = writeJdbcTemplate.update("UPDATE token SET token = ?, revoked = ?, expired = ? WHERE user_id = ? AND revoked = false", accessToken, Boolean.FALSE, Boolean.FALSE, user.getId());

            if (updatedRows == 0) {
                writeJdbcTemplate.update(SqlQuery.InsertQuery.INSERT_TOKEN, accessToken, TokenType.BEARER.name(), Boolean.FALSE, Boolean.FALSE, user.getId());
            }
            log.info("Token updated/saved successfully for user: {}", user.getEmail());
        } catch (Exception e) {
            log.error("Error managing token for user {}: {}", user.getEmail(), e.getMessage());
            throw new RuntimeException("Failed to manage authentication token", e);
        }

        log.info("Generated Token from Authenticate Function: {}", accessToken);

        return AuthenticationResponse.builder().accessToken(Objects.requireNonNull(accessToken)).refreshToken(Objects.requireNonNull(refreshToken)).userName(user.getFirstName() + " " + user.getLastName()).role(String.valueOf(user.getRole())).build();
    }

    @Override
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String authorizationHeader = request.getHeader(Objects.requireNonNull(HttpHeaders.AUTHORIZATION, "Authorization header cannot be null"));
        final String refreshToken;
        final String userEmail;

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            log.error("Authorization Header is Null or Not Started with Bearer");
            return;
        }

        refreshToken = authorizationHeader.substring(7);
        userEmail = jwtServiceImpl.extractUserName(refreshToken);

        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            var userDetails = this.userRepository.findByEmail(userEmail).orElseThrow();
            log.info("User Details from Refresh Token: {}", userDetails);

            if (jwtServiceImpl.isTokenValidated(refreshToken, userDetails)) {
                var accessToken = jwtServiceImpl.generateToken(userDetails);
                log.info("Generated Token from Refresh Token Function: {}", accessToken);

                try {
                    writeJdbcTemplate.update(SqlQuery.InsertQuery.INSERT_TOKEN, accessToken, TokenType.BEARER.name(), Boolean.FALSE, Boolean.FALSE, userDetails.getId());
                    log.info("New access token saved successfully for user: {}", userDetails.getEmail());
                } catch (Exception e) {
                    log.error("Error saving new access token for user {}: {}", userDetails.getEmail(), e.getMessage());
                }

                var authResponse = AuthenticationResponse.builder().accessToken(accessToken).refreshToken(refreshToken).build();
                log.info("Authentication Response from Refresh Token Function: {}", authResponse);

                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }

        final String jwt = authHeader.substring(7);
        var userEmail = jwtServiceImpl.extractUserName(jwt);

        if (userEmail != null) {
            var user = userRepository.findByEmail(userEmail).orElse(null);
            if (user != null) {
                try {
                    writeJdbcTemplate.update(SqlQuery.UpdateQuery.REVOKE_ALL_USER_TOKENS, Boolean.TRUE, Boolean.TRUE, user.getId());
                    log.info("Successfully logged out user: {}", userEmail);
                } catch (Exception e) {
                    log.error("Error during logout for user {}: {}", userEmail, e.getMessage());
                }
            }
        }
    }

    @Override
    public ResponseEntity<APIResponse> getAllAuthentications(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<User> usersPage = userRepository.findAll(pageable);

        List<Map<String, Object>> userDetailsList = usersPage.getContent().stream().map(user -> {
            Map<String, Object> userDetails = new HashMap<>();
            userDetails.put("id", user.getId());
            userDetails.put("firstName", user.getFirstName());
            userDetails.put("lastName", user.getLastName());
            userDetails.put("email", user.getEmail());
            userDetails.put("role", user.getRole());
            userDetails.put("user_profile_pic", user.getUserProfilePic());
            userDetails.put("password", passwordEncoder.encode(user.getPassword()));

            if (user.getRole().equals(Role.DRIVER)) {
                try {
                    log.debug("Fetching driver details for user ID: {}", user.getId());

                    List<Driver> query = readJdbcTemplate.query(SqlQuery.SelectQuery.FIND_DRIVER_BY_ROOT_USER_ID, new Object[]{user.getId()}, (rs, rowNum) -> {
                        try {
                            return Driver.builder().driverRegistrationNumber(rs.getInt("driver_registration_number")).rootUserId(rs.getInt("root_user_id")).driverAddress(rs.getString("driver_address")).driverNIC(rs.getString("driver_nic")).phoneNumber(String.valueOf(rs.getLong("phone_number"))).vehicleAssigned(rs.getString("vehicle_assigned")).driverStatus(rs.getString("driver_status")).emergencyContact(rs.getString("emergency_contact")).dateOfBirth(rs.getDate("date_of_birth")).dateOfJoining(rs.getDate("date_of_joining")).licenseNumber(rs.getString("license_number")).licenseExpiryDate(rs.getDate("license_expiry_date")).licenseImageFront(rs.getString("license_image_front")).licenseImageBack(rs.getString("license_image_back")).build();
                        } catch (SQLException e) {
                            log.error("Error mapping driver row for user ID {}: {}", user.getId(), e.getMessage());
                            throw new RuntimeException("Error mapping driver data", e);
                        }
                    });

                    log.debug("Driver query results size: {}", query.size());

                    if (!query.isEmpty()) {
                        Driver driver = query.get(0);
                        userDetails.put("driver_registration_number", driver.getDriverRegistrationNumber());
                        userDetails.put("root_user_id", driver.getRootUserId());
                        userDetails.put("driver_address", driver.getDriverAddress());
                        userDetails.put("driver_nic", driver.getDriverNIC());
                        userDetails.put("phone_number", driver.getPhoneNumber());
                        userDetails.put("vehicle_assigned", driver.getVehicleAssigned());
                        userDetails.put("driver_status", driver.getDriverStatus());
                        userDetails.put("emergency_contact", driver.getEmergencyContact());
                        userDetails.put("date_of_birth", driver.getDateOfBirth());
                        userDetails.put("date_of_joining", driver.getDateOfJoining());
                        userDetails.put("license_number", driver.getLicenseNumber());
                        userDetails.put("license_expiry_date", driver.getLicenseExpiryDate());
                        userDetails.put("license_image_front", driver.getLicenseImageFront());
                        userDetails.put("license_image_back", driver.getLicenseImageBack());


                    } else {
                        log.warn("No driver details found for user ID: {}", user.getId());
                    }
                } catch (Exception e) {
                    log.error("Error while getting driver details for user ID {}: {}", user.getId(), e.getMessage(), e);
                }
            } else if (user.getRole().equals(Role.CUSTOMER)) {
                try {
                    List<Customer> query = readJdbcTemplate.query(SqlQuery.SelectQuery.FIND_CUSTOMER_BY_ROOT_USER_ID, new Object[]{user.getId()}, (rs, rowNum) -> Customer.builder().registrationNumber(rs.getInt("registration_number")).rootUserId(rs.getInt("root_user_id")).address(rs.getString("address")).NIC(rs.getString("nic")).phoneNumber(String.valueOf(rs.getLong("phone_number"))).build());

                    log.debug("Customer query results size: {}", query.size());

                    if (!query.isEmpty()) {
                        Customer customer = query.get(0);
                        userDetails.put("registration_number", customer.getRegistrationNumber());
                        userDetails.put("root_user_id", customer.getRootUserId());
                        userDetails.put("address", customer.getAddress());
                        userDetails.put("nic", customer.getNIC());
                        userDetails.put("phone_number", customer.getPhoneNumber());
                    } else {
                        log.warn("No customer details found for user ID: {}", user.getId());
                    }
                } catch (Exception e) {
                    log.error("Error while getting customer details for user ID {}: {}", user.getId(), e.getMessage(), e);
                    throw new RuntimeException("Error retrieving customer data", e);
                }
            } else if (user.getRole().equals(Role.ADMIN)) {
                try {
                    List<Manager> query = readJdbcTemplate.query(SqlQuery.SelectQuery.FIND_MANAGER_BY_ROOT_USER_ID, new Object[]{user.getId()}, (rs, rowNum) -> Manager.builder().registrationNumber(rs.getInt("registration_number")).rootUserId(rs.getInt("root_user_id")).address(rs.getString("address")).NIC(rs.getString("nic")).phoneNumber(String.valueOf(rs.getLong("phone_number"))).build());
                    if (!query.isEmpty()) {
                        Manager manager = query.get(0);
                        userDetails.put("registration_number", manager.getRegistrationNumber());
                        userDetails.put("root_user_id", manager.getRootUserId());
                        userDetails.put("address", manager.getAddress());
                        userDetails.put("nic", manager.getNIC());
                        userDetails.put("phone_number", manager.getPhoneNumber());
                    }
                } catch (Exception e) {
                    log.error("Error while getting manager details: {}", e.getMessage());
                }
            }

            return userDetails;
        }).toList();

        return responseUtil.wrapSuccess(userDetailsList, HttpStatus.OK);
    }
}