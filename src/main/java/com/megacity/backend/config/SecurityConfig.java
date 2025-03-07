package com.megacity.backend.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

import static com.megacity.backend.domain.enums.Permission.*;
import static com.megacity.backend.domain.enums.Role.*;
import static org.springframework.http.HttpMethod.*;

/**
 * Security configuration class for the MegaCity backend application.
 * Configures Spring Security with JWT authentication, role-based access control,
 * and CORS settings for the REST API.
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    /**
     * Array of URL patterns that don't require authentication
     */
    private static final String[] WHITE_LIST_URL = {
            "/api/v1/auth/**",
            "api/v1/guideline/**",
            "/api/v1/auth/register",
            "/api/v1/web-content/public/**",
            "/api/v1/booking/**",
            "/api/v1/driver/fetch-all",
            "/api/v1/vehicle/fetch-all/**",
            "/api/v1/booking/filter/**"
    };
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final LogoutHandler logoutHandler;

    /**
     * Configures the security filter chain for HTTP requests.
     * Sets up authentication, authorization, CORS, and logout handling.
     *
     * @param http HttpSecurity object to configure
     * @return Configured SecurityFilterChain
     * @throws Exception if configuration fails
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(WHITE_LIST_URL).permitAll()

                        .requestMatchers("/api/v1/booking/**").hasAnyRole(ADMIN.name(), CUSTOMER.name(), DRIVER.name())
                        .requestMatchers(GET, "/api/v1/booking/**").hasAuthority(ADMIN_READ.name())
                        .requestMatchers(POST, "/api/v1/booking/**").hasAuthority(ADMIN_CREATE.name())
                        .requestMatchers(PUT, "/api/v1/booking/**").hasAuthority(ADMIN_UPDATE.name())
                        .requestMatchers(DELETE, "/api/v1/booking/**").hasAuthority(ADMIN_DELETE.name())

                        .requestMatchers("/api/v1/customer/**").hasAnyRole(ADMIN.name(), CUSTOMER.name())
                        .requestMatchers(GET, "/api/v1/customer/**").hasAuthority(ADMIN_READ.name())
                        .requestMatchers(POST, "/api/v1/customer/**").hasAuthority(ADMIN_CREATE.name())
                        .requestMatchers(PUT, "/api/v1/customer/**").hasAuthority(ADMIN_UPDATE.name())
                        .requestMatchers(DELETE, "/api/v1/customer/**").hasAuthority(ADMIN_DELETE.name())

                        .requestMatchers("/api/v1/driver/**").hasAnyRole(ADMIN.name(), DRIVER.name())
                        .requestMatchers(GET, "/api/v1/driver/**").hasAuthority(ADMIN_READ.name())
                        .requestMatchers(POST, "/api/v1/driver/**").hasAuthority(ADMIN_CREATE.name())
                        .requestMatchers(PUT, "/api/v1/driver/**").hasAuthority(ADMIN_UPDATE.name())
                        .requestMatchers(DELETE, "/api/v1/driver/**").hasAuthority(ADMIN_DELETE.name())

                        .requestMatchers("/api/v1/guideline/**").hasAnyRole(ADMIN.name(), CUSTOMER.name())
                        .requestMatchers(GET, "/api/v1/guideline/**").hasAuthority(ADMIN_READ.name())
                        .requestMatchers(POST, "/api/v1/guideline/**").hasAuthority(ADMIN_CREATE.name())
                        .requestMatchers(PUT, "/api/v1/guideline/**").hasAuthority(ADMIN_UPDATE.name())
                        .requestMatchers(DELETE, "/api/v1/guideline/**").hasAuthority(ADMIN_DELETE.name())

                        .requestMatchers("/api/v1/manager/**").hasRole(ADMIN.name())
                        .requestMatchers(GET, "/api/v1/manager/**").hasAuthority(ADMIN_READ.name())
                        .requestMatchers(POST, "/api/v1/manager/**").hasAuthority(ADMIN_CREATE.name())
                        .requestMatchers(PUT, "/api/v1/manager/**").hasAuthority(ADMIN_UPDATE.name())
                        .requestMatchers(DELETE, "/api/v1/manager/**").hasAuthority(ADMIN_DELETE.name())

                        .requestMatchers("/api/v1/report/**").hasAnyRole(ADMIN.name(), CUSTOMER.name())
                        .requestMatchers(GET, "/api/v1/report/**").hasAuthority(ADMIN_READ.name())
                        .requestMatchers(POST, "/api/v1/report/**").hasAuthority(ADMIN_CREATE.name())
                        .requestMatchers(PUT, "/api/v1/report/**").hasAuthority(ADMIN_UPDATE.name())
                        .requestMatchers(DELETE, "/api/v1/report/**").hasAuthority(ADMIN_DELETE.name())

                        .requestMatchers("/api/v1/vehicle/**").hasAnyRole(ADMIN.name(), CUSTOMER.name())
                        .requestMatchers(GET, "/api/v1/vehicle/**").hasAuthority(ADMIN_READ.name())
                        .requestMatchers(POST, "/api/v1/vehicle/**").hasAuthority(ADMIN_CREATE.name())
                        .requestMatchers(PUT, "/api/v1/vehicle/**").hasAuthority(ADMIN_UPDATE.name())
                        .requestMatchers(DELETE, "/api/v1/vehicle/**").hasAuthority(ADMIN_DELETE.name())

                        .requestMatchers("/api/v1/web-content/**").hasRole(ADMIN.name())
                        .requestMatchers(GET, "/api/v1/web-content/**").hasAuthority(ADMIN_READ.name())
                        .requestMatchers(POST, "/api/v1/web-content/**").hasAuthority(ADMIN_CREATE.name())
                        .requestMatchers(PUT, "/api/v1/web-content/**").hasAuthority(ADMIN_UPDATE.name())
                        .requestMatchers(DELETE, "/api/v1/web-content/**").hasAuthority(ADMIN_DELETE.name())

                        .anyRequest().authenticated()
                )

                .logout(logout -> logout
                        .logoutUrl("/api/v1/auth/logout")
                        .addLogoutHandler(logoutHandler)
                        .logoutSuccessHandler((request, response, authentication) ->
                                SecurityContextHolder.clearContext())
                );

        return http.build();
    }

    /**
     * Configures CORS settings for the application.
     * Defines allowed origins, methods, and headers for cross-origin requests.
     *
     * @return CorsConfigurationSource with configured CORS settings
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList(
                "http://localhost:3000",
                "http://localhost:5173"
        ));
        configuration.setAllowedMethods(Arrays.asList(
                HttpMethod.GET.name(),
                HttpMethod.POST.name(),
                HttpMethod.PUT.name(),
                HttpMethod.DELETE.name(),
                HttpMethod.OPTIONS.name()
        ));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setExposedHeaders(List.of("Authorization"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}