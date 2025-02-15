package com.megacity.backend.authentication.controller;

import com.megacity.backend.domain.request.AuthenticationRequest;
import com.megacity.backend.domain.request.RegistrationRequest;
import com.megacity.backend.domain.response.APIResponse;
import com.megacity.backend.domain.response.AuthenticationResponse;
import com.megacity.backend.authentication.service.AuthenticationServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthManagementController {

    @NonNull
    private final AuthenticationServiceImpl authenticationServiceImpl;

    /**
     * Handles the registration of a new user.
     *
     * @param registrationRequest the registration request containing user details
     * @return a ResponseEntity containing the authentication response
     */
    @PostMapping("/public/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegistrationRequest registrationRequest) {
        System.out.println("RegistrationRequest: " + registrationRequest.toString());
        log.info("RegistrationRequest: {}", registrationRequest);
        return ResponseEntity.ok(authenticationServiceImpl.register(registrationRequest));
    }

    /**
     * Authenticates a user based on the provided authentication request.
     *
     * @param request the authentication request containing user credentials
     * @return a ResponseEntity containing the authentication response
     */
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest request) {
        log.info("AuthenticationRequest: {}", request.toString());
        return ResponseEntity.ok(authenticationServiceImpl.authenticate(request));
    }

    /**
     * Refreshes the authentication token.
     *
     * @param request  the HTTP request containing the refresh token
     * @param response the HTTP response to be sent back to the client
     * @throws IOException if an input or output exception occurs
     */
    @PostMapping("/refresh")
    public void refresh(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.info("Refresh Request: {} Response: {}", request.toString(), response.toString());
        authenticationServiceImpl.refreshToken(request, response);
    }

    @PostMapping("/get-all-users")
    public ResponseEntity<APIResponse> getAllUsers(@RequestParam Integer page, @RequestParam Integer size) {
        log.info("Get All Users");
        return authenticationServiceImpl.getAllAuthentications(page, size);
    }


}
