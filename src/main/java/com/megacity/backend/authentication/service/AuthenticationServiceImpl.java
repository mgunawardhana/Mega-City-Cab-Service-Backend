package com.megacity.backend.authentication.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.megacity.backend.authentication.repository.UserRepository;
import com.megacity.backend.customer_management.repository.CustomerRepository;
import com.megacity.backend.domain.entity.Customer;
import com.megacity.backend.domain.entity.Driver;
import com.megacity.backend.domain.entity.User;
import com.megacity.backend.domain.enums.Role;
import com.megacity.backend.domain.request.AuthenticationRequest;
import com.megacity.backend.domain.request.RegistrationRequest;
import com.megacity.backend.domain.response.AuthenticationResponse;
import com.megacity.backend.driver_management.repository.DriverRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl {

    @NonNull
    private final UserRepository userRepository;
    @NonNull
    private final PasswordEncoder passwordEncoder;
    @NonNull
    private final JwtServiceImpl jwtServiceImpl;
    @NonNull
    private final AuthenticationManager authenticationManager;
    @NonNull
    private final CustomerRepository customerRepository;
    @NonNull
    private final DriverRepository driverRepository;

    /**
     * Registers a new user in the system.
     *
     * @param registrationRequest the registration request containing user details
     * @return an AuthenticationResponse containing the access and refresh tokens
     */
    public AuthenticationResponse register(RegistrationRequest registrationRequest) {
        var user = User.builder().firstName(registrationRequest.getFirstName()).lastName(registrationRequest.getLastName()).email(registrationRequest.getEmail()).password(passwordEncoder.encode(registrationRequest.getPassword())).role(registrationRequest.getRole()).build();

        var customer = Customer.builder().address(registrationRequest.getAddress()).phoneNumber(registrationRequest.getPhoneNumber()).user(user).build();

        var driver = Driver.builder().licenseNumber(registrationRequest.getLicenseNumber()).vehicleDetails(registrationRequest.getVehicleDetails()).user(user).build();

        userRepository.save(user);
        switch (registrationRequest.getRole()) {
            case CUSTOMER:
                log.info("Customer Registration");
                customerRepository.save(customer);
                break;
            case DRIVER:
                log.info("Driver Registration");
                driverRepository.save(driver);
                break;
            default:
                log.warn("Unsupported role: {}", registrationRequest.getRole());
                throw new IllegalArgumentException("Unsupported role: " + registrationRequest.getRole());
        }

        var jwtToken = jwtServiceImpl.generateToken(Objects.requireNonNull(user));
        var refreshToken = jwtServiceImpl.generateRefreshToken(Objects.requireNonNull(user));

        log.info("Generated Token from Register Function: {}", jwtToken);

        return AuthenticationResponse.builder().accessToken(jwtToken).refreshToken(refreshToken).build();
    }

    /**
     * Authenticates a user based on the provided authentication request.
     *
     * @param request the authentication request containing user credentials
     * @return an AuthenticationResponse containing the access and refresh tokens
     */
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        var user = userRepository.findByEmail(request.getEmail()).orElseThrow();

        log.info("AuthenticationResponse From Authenticate Function: {}", user);

        var jwtToken = jwtServiceImpl.generateToken(Objects.requireNonNull(user));
        var refreshToken = jwtServiceImpl.generateRefreshToken(user);

        log.info("Generated Token from Authenticate Function: {}", jwtToken);
        return AuthenticationResponse.builder().accessToken(Objects.requireNonNull(jwtToken)).refreshToken(Objects.requireNonNull(refreshToken)).build();
    }

    /**
     * Refreshes the authentication token.
     *
     * @param request  the HTTP request containing the refresh token
     * @param response the HTTP response to be sent back to the client
     * @throws IOException if an input or output exception occurs
     */
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
}
