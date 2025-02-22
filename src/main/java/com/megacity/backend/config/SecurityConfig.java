package com.megacity.backend.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.web.cors.CorsConfiguration;

import static com.megacity.backend.domain.enums.Permission.*;
import static com.megacity.backend.domain.enums.Role.ADMIN;
import static com.stripe.param.financialconnections.SessionCreateParams.AccountHolder.Type.CUSTOMER;
import static org.springframework.http.HttpMethod.*;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {

    @NonNull
    private final JwtAuthenticationFilter jwtAuthFilter;

    @NonNull
    private final AuthenticationProvider authenticationProvider;

    private static final String[] WHITE_LIST_URL = {"/api/v1/auth/**"};

    private final LogoutHandler logoutHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .cors(corsConfigurer -> corsConfigurer.configurationSource(request -> {
                    var cors = new CorsConfiguration();
                    cors.addAllowedOrigin("http://localhost:3000");
                    cors.addAllowedOrigin("http://localhost:5173");
                    cors.addAllowedMethod("*");
                    cors.addAllowedHeader("*");
                    cors.setAllowCredentials(true);
                    return cors;
                }))
                .authorizeHttpRequests(req ->
                        req.requestMatchers(WHITE_LIST_URL)
                                .permitAll()

                                /* booking */
                                .requestMatchers("/api/v1/booking/**").hasAnyRole(ADMIN.name())
                                .requestMatchers(GET, "/api/v1/booking/**").hasAnyAuthority(ADMIN_READ.name())
                                .requestMatchers(POST, "/api/v1/booking/**").hasAnyAuthority(ADMIN_CREATE.name())
                                .requestMatchers(PUT, "/api/v1/booking/**").hasAnyAuthority(ADMIN_UPDATE.name())
                                .requestMatchers(DELETE, "/api/v1/booking/**").hasAnyAuthority(ADMIN_DELETE.name())

                                /* customer */
                                .requestMatchers("/api/v1/customer/**").hasAnyRole(ADMIN.name(), CUSTOMER.name())
                                .requestMatchers(GET, "/api/v1/customer/**").hasAnyAuthority(ADMIN_READ.name())
                                .requestMatchers(POST, "/api/v1/customer/**").hasAnyAuthority(ADMIN_CREATE.name())
                                .requestMatchers(PUT, "/api/v1/customer/**").hasAnyAuthority(ADMIN_UPDATE.name())
                                .requestMatchers(DELETE, "/api/v1/customer/**").hasAnyAuthority(ADMIN_DELETE.name())

                                /* driver */
                                .requestMatchers("/api/v1/driver/**").hasAnyRole(ADMIN.name(), CUSTOMER.name())
                                .requestMatchers(GET, "/api/v1/driver/**").hasAnyAuthority(ADMIN_READ.name())
                                .requestMatchers(POST, "/api/v1/driver/**").hasAnyAuthority(ADMIN_CREATE.name())
                                .requestMatchers(PUT, "/api/v1/driver/**").hasAnyAuthority(ADMIN_UPDATE.name())
                                .requestMatchers(DELETE, "/api/v1/driver/**").hasAnyAuthority(ADMIN_DELETE.name())

                                /* guideline */
                                .requestMatchers("/api/v1/guideline/**").hasAnyRole(ADMIN.name(), CUSTOMER.name())
                                .requestMatchers(GET, "/api/v1/guideline/**").hasAnyAuthority(ADMIN_READ.name())
                                .requestMatchers(POST, "/api/v1/guideline/**").hasAnyAuthority(ADMIN_CREATE.name())
                                .requestMatchers(PUT, "/api/v1/guideline/**").hasAnyAuthority(ADMIN_UPDATE.name())
                                .requestMatchers(DELETE, "/api/v1/guideline/**").hasAnyAuthority(ADMIN_DELETE.name())

                                /* manager */
                                .requestMatchers("/api/v1/manager/**").hasAnyRole(ADMIN.name(), CUSTOMER.name())
                                .requestMatchers(GET, "/api/v1/manager/**").hasAnyAuthority(ADMIN_READ.name())
                                .requestMatchers(POST, "/api/v1/manager/**").hasAnyAuthority(ADMIN_CREATE.name())
                                .requestMatchers(PUT, "/api/v1/manager/**").hasAnyAuthority(ADMIN_UPDATE.name())
                                .requestMatchers(DELETE, "/api/v1/manager/**").hasAnyAuthority(ADMIN_DELETE.name())

                                /* report */
                                .requestMatchers("/api/v1/report/**").hasAnyRole(ADMIN.name(), CUSTOMER.name())
                                .requestMatchers(GET, "/api/v1/report/**").hasAnyAuthority(ADMIN_READ.name())
                                .requestMatchers(POST, "/api/v1/report/**").hasAnyAuthority(ADMIN_CREATE.name())
                                .requestMatchers(PUT, "/api/v1/report/**").hasAnyAuthority(ADMIN_UPDATE.name())
                                .requestMatchers(DELETE, "/api/v1/report/**").hasAnyAuthority(ADMIN_DELETE.name())

                                /* vehicle */
                                .requestMatchers("/api/v1/vehicle/**").hasAnyRole(ADMIN.name(), CUSTOMER.name())
                                .requestMatchers(GET, "/api/v1/vehicle/**").hasAnyAuthority(ADMIN_READ.name())
                                .requestMatchers(POST, "/api/v1/vehicle/**").hasAnyAuthority(ADMIN_CREATE.name())
                                .requestMatchers(PUT, "/api/v1/vehicle/**").hasAnyAuthority(ADMIN_UPDATE.name())
                                .requestMatchers(DELETE, "/api/v1/vehicle/**").hasAnyAuthority(ADMIN_DELETE.name())

                                /* web-content */
                                .requestMatchers("/api/v1/web-content/**").hasAnyRole(ADMIN.name(), CUSTOMER.name())
                                .requestMatchers(GET, "/api/v1/web-content/**").hasAnyAuthority(ADMIN_READ.name())
                                .requestMatchers(POST, "/api/v1/web-content/**").hasAnyAuthority(ADMIN_CREATE.name())
                                .requestMatchers(PUT, "/api/v1/web-content/**").hasAnyAuthority(ADMIN_UPDATE.name())
                                .requestMatchers(DELETE, "/api/v1/web-content/**").hasAnyAuthority(ADMIN_DELETE.name())

                                .anyRequest()
                                .authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(logout ->
                        logout.logoutUrl("/api/v1/auth/logout")
                                .addLogoutHandler(logoutHandler)
                                .logoutSuccessHandler((request, response, authentication) ->
                                        SecurityContextHolder.clearContext())
                );
        return http.build();
    }
}