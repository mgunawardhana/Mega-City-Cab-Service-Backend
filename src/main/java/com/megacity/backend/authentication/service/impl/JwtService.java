package com.megacity.backend.authentication.service.impl;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;
import java.util.function.Function;

public interface JwtService {
    /**
     * Extracts the username from the given JWT token.
     *
     * @param token the JWT token
     * @return the username extracted from the token
     */
    String extractUserName(String token);

    /**
     * Extracts a specific claim from the given JWT token using the provided claims resolver function.
     *
     * @param token          the JWT token
     * @param claimsResolver the function to resolve the claim from the token's claims
     * @param <T>            the type of the claim
     * @return the extracted claim
     */
    <T> T extractClaim(String token, Function<Claims, T> claimsResolver);

    /**
     * Generates a JWT token for the given user details.
     *
     * @param userDetails the user details
     * @return the generated JWT token
     */
    String generateToken(UserDetails userDetails);

    /**
     * Generates a JWT token with additional claims for the given user details.
     *
     * @param extraClaims the additional claims to include in the token
     * @param userDetails the user details
     * @return the generated JWT token
     */
    String generateToken(Map<String, Object> extraClaims, UserDetails userDetails);

    /**
     * Generates a refresh token for the given user details.
     *
     * @param userDetails the user details
     * @return the generated refresh token
     */
    String generateRefreshToken(UserDetails userDetails);

    /**
     * Validates the given JWT token against the provided user details.
     *
     * @param token       the JWT token
     * @param userDetails the user details
     * @return true if the token is valid, false otherwise
     */
    boolean isTokenValidated(String token, UserDetails userDetails);
}
