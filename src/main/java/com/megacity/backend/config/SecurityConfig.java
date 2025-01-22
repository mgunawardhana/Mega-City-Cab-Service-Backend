package com.megacity.backend.config;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @NonNull
    private final JwtAuthenticationFilter jwtAuthFilter;

    @NonNull
    private final AuthenticationProvider authenticationProvider;

    /**
     * Configures the security filter chain for the application.
     * <p>
     * This method sets up the security configuration for HTTP requests, including disabling CSRF,
     * enabling CORS with default settings, specifying authorization rules, setting session management
     * to stateless, and adding a JWT authentication filter before the UsernamePasswordAuthenticationFilter.
     *
     * @param http the HttpSecurity object to configure
     * @return the configured SecurityFilterChain
     * @throws Exception if an error occurs while configuring the security filter chain
     */
    @SneakyThrows
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable).cors(withDefaults()).authorizeHttpRequests(auth -> {
            //without authentication
//            auth.requestMatchers("/api/v1/auth/public/register").permitAll();
//            auth.requestMatchers("api/v1/customer/**").permitAll();
            auth.requestMatchers("api/v1/**").permitAll();

            //with authentication
//            auth.requestMatchers("/api/v1/auth/**").fullyAuthenticated();
            auth.anyRequest().authenticated();
        }).sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)).authenticationProvider(authenticationProvider).addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}