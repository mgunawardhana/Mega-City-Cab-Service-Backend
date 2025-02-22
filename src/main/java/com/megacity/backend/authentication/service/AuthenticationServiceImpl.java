package com.megacity.backend.authentication.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.megacity.backend.authentication.repository.UserRepository;
import com.megacity.backend.authentication.service.impl.AuthenticationService;
import com.megacity.backend.constant.SqlQuery;
import com.megacity.backend.domain.entity.Customer;
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

    /**
     * Registers a new user in the system.
     *
     * @param registrationRequest the registration request containing user details
     * @return an AuthenticationResponse containing the access and refresh tokens
     */
    @Override
    @Transactional
    public AuthenticationResponse register(RegistrationRequest registrationRequest) {
        try {
            // Create user entity
            var user = User.builder()
                    .firstName(registrationRequest.getFirstName())
                    .lastName(registrationRequest.getLastName())
                    .email(registrationRequest.getEmail())
                    .password(passwordEncoder.encode(registrationRequest.getPassword()))
                    .role(registrationRequest.getRole())
                    .build();

            log.info("Processing registration for user: {}", user.getEmail());

            // Save user
            User savedUser = userRepository.save(user);

            // Handle role-specific data
            try {
                if (savedUser.getRole().equals(Role.USER)) {
                    writeJdbcTemplate.update(SqlQuery.InsertQuery.ADD_NEW_CUSTOMER,
                            savedUser.getId(),
                            registrationRequest.getAddress(),
                            registrationRequest.getNic(),
                            registrationRequest.getPhone_number()
                    );
                    log.info("New Customer profile created successfully");
                } else if (savedUser.getRole().equals(Role.ADMIN)) {
                    writeJdbcTemplate.update(SqlQuery.InsertQuery.ADD_NEW_MANAGER,
                            savedUser.getId(),
                            registrationRequest.getAddress(),
                            registrationRequest.getNic(),
                            registrationRequest.getPhone_number()
                    );
                    log.info("New Manager profile created successfully");
                }
            } catch (Exception e) {
                log.error("Error creating profile for user {}: {}", savedUser.getEmail(), e.getMessage());
                throw new RuntimeException("Failed to create user profile", e);
            }

            // Generate tokens
            String accessToken = jwtServiceImpl.generateToken(savedUser);
            String refreshToken = jwtServiceImpl.generateRefreshToken(savedUser);

            // Save token - Fixed boolean parameters
            try {
                writeJdbcTemplate.update(SqlQuery.InsertQuery.INSERT_TOKEN,
                        1000,
                        accessToken,
                        TokenType.BEARER.name(),
                        Boolean.FALSE,
                        Boolean.FALSE ,
                        savedUser.getId()
                );
                log.info("Token saved successfully for user: {}", savedUser.getEmail());
            } catch (Exception e) {
                log.error("Error saving token for user {}: {}", savedUser.getEmail(), e.getMessage());
                throw new RuntimeException("Failed to save authentication token", e);
            }

            // Return authentication response
            return AuthenticationResponse.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .build();

        } catch (Exception e) {
            log.error("Registration failed: {}", e.getMessage());
            throw new RuntimeException("Registration failed", e);
        }
    }
    /**
     * Authenticates a user based on the provided authentication request.
     *
     * @param request the authentication request containing user credentials
     * @return an AuthenticationResponse containing the access and refresh tokens
     */
    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        var user = userRepository.findByEmail(request.getEmail()).orElseThrow();

        log.info("AuthenticationResponse From Authenticate Function: {}", user);

        var jwtToken = jwtServiceImpl.generateToken(Objects.requireNonNull(user));
        var refreshToken = jwtServiceImpl.generateRefreshToken(user);

        log.info("Generated Token from Authenticate Function: {}", jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(Objects.requireNonNull(jwtToken))
                .refreshToken(Objects.requireNonNull(refreshToken))
                .build();
    }

    /**
     * Refreshes the authentication token.
     *
     * @param request  the HTTP request containing the refresh token
     * @param response the HTTP response to be sent back to the client
     * @throws IOException if an input or output exception occurs
     */
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

                var authResponse = AuthenticationResponse.builder().accessToken(accessToken).refreshToken(refreshToken).build();
                log.info("Authentication Response from Refresh Token Function: {}", authResponse);

                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
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

            userDetails.put("password", passwordEncoder.encode(user.getPassword()));




        // Fetch user-related details based on role
            if (user.getRole().equals(Role.USER)) {
                try {
                    List<Customer> query = readJdbcTemplate.query(
                            SqlQuery.SelectQuery.FIND_CUSTOMER_BY_ROOT_USER_ID,
                            new Object[]{user.getId()},
                            (rs, rowNum) -> Customer.builder()
                                    .registrationNumber(Integer.parseInt(rs.getString("registration_number")))
                                    .rootUserId(Integer.parseInt(rs.getString("root_user_id")))
                                    .address(rs.getString("address"))
                                    .NIC(rs.getString("nic"))
                                    .phoneNumber(String.valueOf(rs.getLong("phone_number")))
                                    .build()
                    );
                    if (!query.isEmpty()) {
                        Customer customer = query.get(0);
                        userDetails.put("registration_number", customer.getRegistrationNumber());
                        userDetails.put("root_user_id", customer.getRootUserId());
                        userDetails.put("address", customer.getAddress());
                        userDetails.put("nic", customer.getNIC());
                        userDetails.put("phone_number", customer.getPhoneNumber());
                    }
                } catch (Exception e) {
                    log.error("Error while getting customer details: {}", e.getMessage());
                }
            }

            if (user.getRole().equals(Role.ADMIN)) {
                try {
                    List<Manager> query = readJdbcTemplate.query(
                            SqlQuery.SelectQuery.FIND_MANAGER_BY_ROOT_USER_ID,
                            new Object[]{user.getId()},
                            (rs, rowNum) -> Manager.builder()
                                    .registrationNumber(Integer.parseInt(rs.getString("registration_number")))
                                    .rootUserId(Integer.parseInt(rs.getString("root_user_id")))
                                    .address(rs.getString("address"))
                                    .NIC(rs.getString("nic"))
                                    .phoneNumber(String.valueOf(rs.getLong("phone_number")))
                                    .build()
                    );
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
