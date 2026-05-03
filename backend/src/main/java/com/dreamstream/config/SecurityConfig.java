package com.dreamstream.config;

import com.dreamstream.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    static final String REQUEST_OFFER_PATH_PATTERN = "/api/requests/*/offers";

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthenticationFilter jwtAuthenticationFilter)
            throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint((request, response, authException) -> response.sendError(401))
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/register", "/api/auth/login", "/actuator/health", "/api/health").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/my-requests").authenticated()
                        .requestMatchers(HttpMethod.POST, "/api/requests/**").authenticated()
                        // UUID request ids are a single path segment, so use * instead of ** here.
                        .requestMatchers(HttpMethod.POST, REQUEST_OFFER_PATH_PATTERN).authenticated()
                        .requestMatchers(HttpMethod.PUT, "/api/requests/**").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/api/requests/**").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/my-offers").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/api/offers/**").authenticated()
                        .anyRequest().permitAll()
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
