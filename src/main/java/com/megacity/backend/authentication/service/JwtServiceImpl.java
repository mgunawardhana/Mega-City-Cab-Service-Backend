package com.megacity.backend.authentication.service;

import com.megacity.backend.authentication.service.impl.JwtService;
import com.megacity.backend.domain.entity.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Slf4j
@Service
public class JwtServiceImpl implements JwtService {

    @NonNull
    @Value("${application.security.jwt.secret-key}")
    private String secretKey;

    @Value("${application.security.jwt.expiration}")
    private long jwtExpiration;

    @Value("${application.security.jwt.refresh-token.expiration}")
    private long refreshExpiration;

    /**
     * Extracts the username from the provided JWT token.
     *
     * @param token the JWT token from which to extract the username
     * @return the username extracted from the token
     */
    @Override
    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extracts a specific claim from the provided JWT token using the given claims resolver function.
     *
     * @param token          the JWT token from which to extract the claim
     * @param claimsResolver the function to resolve the claim from the token
     * @param <T>            the type of the claim to be extracted
     * @return the extracted claim
     */
    @Override
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Generates a JWT token for the given user details.
     *
     * @param userDetails the user details for which to generate the token
     * @return the generated JWT token
     */
    @Override
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    /**
     * Generates a JWT token with additional claims for the given user details.
     *
     * @param extraClaims additional claims to include in the token
     * @param userDetails the user details for which to generate the token
     * @return the generated JWT token
     */
    @Override
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return buildToken(extraClaims, userDetails, jwtExpiration);
    }

    /**
     * Generates a refresh JWT token for the given user details.
     *
     * @param userDetails the user details for which to generate the refresh token
     * @return the generated refresh JWT token
     */
    @Override
    public String generateRefreshToken(UserDetails userDetails) {
        return buildToken(new HashMap<>(), userDetails, refreshExpiration);
    }

    /**
     * Builds a JWT token with the specified claims, user details, and expiration time.
     *
     * @param extractClaims the claims to include in the token
     * @param userDetails   the user details for which to build the token
     * @param expiration    the expiration time for the token
     * @return the built JWT token
     */
    private String buildToken(Map<String, Object> extractClaims, UserDetails userDetails, long expiration) {
        extractClaims.put("role", ((User) userDetails).getRole().name()); // Make sure the role is added
        return Jwts.builder()
                .setClaims(extractClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }



    /**
     * Validates the provided JWT token against the user details.
     *
     * @param token       the JWT token to be validated
     * @param userDetails the user details to validate the token against
     * @return true if the token is valid, false otherwise
     */
    @Override
    public boolean isTokenValidated(String token, UserDetails userDetails) {
        final String username = extractUserName(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }


    /**
     * Checks if the provided JWT token is expired.
     *
     * @param token the JWT token to check
     * @return true if the token is expired, false otherwise
     */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Extracts the expiration date from the provided JWT token.
     *
     * @param token the JWT token to extract the expiration date from
     * @return the expiration date of the token
     */
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Extracts all claims from the provided JWT token.
     *
     * @param token the JWT token to extract claims from
     * @return the claims extracted from the token
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJws(token).getBody();
    }

    /**
     * Retrieves the signing key used to sign the JWT token.
     *
     * @return the signing key
     */
    private Key getSignInKey() {
        byte[] secretBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(secretBytes);
    }


}
