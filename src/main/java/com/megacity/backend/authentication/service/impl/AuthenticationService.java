package com.megacity.backend.authentication.service.impl;

import com.megacity.backend.domain.request.AuthenticationRequest;
import com.megacity.backend.domain.request.RegistrationRequest;
import com.megacity.backend.domain.response.APIResponse;
import com.megacity.backend.domain.response.AuthenticationResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

public interface AuthenticationService {

    /**
     * Registers a new user with the given registration details.
     *
     * @param registrationRequest the request object containing user registration details
     * @return an {@link AuthenticationResponse} containing authentication details of the registered user
     */
    AuthenticationResponse register(RegistrationRequest registrationRequest);

    /**
     * Authenticates a user based on the provided credentials.
     *
     * @param request the authentication request containing user credentials
     * @return an {@link AuthenticationResponse} containing authentication details if successful
     */
    AuthenticationResponse authenticate(AuthenticationRequest request);

    /**
     * Refreshes the authentication token for the user.
     *
     * @param request  the HTTP servlet request containing authentication details
     * @param response the HTTP servlet response to send the refreshed token
     * @throws IOException if an error occurs during token refresh
     */
    void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;


    void logout(HttpServletRequest request, HttpServletResponse response) throws IOException;

    ResponseEntity<APIResponse> getAllAuthentications(int page, int size);
}
