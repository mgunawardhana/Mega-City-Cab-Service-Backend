package com.megacity.backend.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    @NonNull
    private final JwtAuthenticationFilter jwtAuthFilter;

    @NonNull
    private final AuthenticationProvider authenticationProvider;

    /**
     * Configures the security filter chain for the application.
     *
     * @param http the HttpSecurity object to configure
     * @return the configured SecurityFilterChain
     * @throws Exception if an error occurs while configuring the security filter chain
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrfConfigurer -> csrfConfigurer.disable()) // Disable CSRF
                .cors(corsConfigurer -> corsConfigurer.configurationSource(request -> {
                    var cors = new org.springframework.web.cors.CorsConfiguration();
                    cors.addAllowedOrigin("http://localhost:3000");
                    cors.addAllowedOrigin("http://localhost:5173");
                    cors.addAllowedMethod("*");
                    cors.addAllowedHeader("*");
                    return cors;
                }))
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/api/v1/**").permitAll();
                    auth.requestMatchers("/api/v1/auth/public/**").permitAll();
                    auth.requestMatchers("/api/v1/auth/authenticate").permitAll();
                    auth.requestMatchers("/api/v1/auth/refresh").permitAll();
                    auth.anyRequest().authenticated();
                })
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
