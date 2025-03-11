package org.roche.perscholas_capstone_addiction_help.config;

import org.roche.perscholas_capstone_addiction_help.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers("/css/**", "/js/**", "/images/**", "/login", "/register").permitAll()  // Public access
                        .requestMatchers("/admin/**").hasRole("ADMIN")  // Employee task hub
                        .requestMatchers("/treatments/**").hasRole("USER")  // Admin access for managing treatments and uploading tasks
                        .anyRequest().authenticated())  // All other requests require authentication

                .formLogin(formLogin -> formLogin
                        .loginPage("/login")
                        .permitAll()
                        .successHandler((request, response, authentication) -> {
                            authentication.getAuthorities().forEach(grantedAuthority -> {
                                String role = grantedAuthority.getAuthority();
                                try {
                                    if (role.equals("ROLE_ADMIN")) {
                                        response.sendRedirect("/admin/treatment-approvals");  // Admin redirect
                                    } else {
                                        response.sendRedirect("/treatments");  // User or Guest redirect, no authentication required
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            });
                        }))
                .logout(logout -> logout.logoutSuccessUrl("/login").permitAll())
                .userDetailsService(userDetailsService);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}