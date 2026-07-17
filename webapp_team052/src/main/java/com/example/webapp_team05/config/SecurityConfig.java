package com.example.webapp_team05.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
public class SecurityConfig {

    // Cifra e controlla le password
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Dice a Spring Security di cercare utenti e ruoli nel database H2
    @Bean
    public JdbcUserDetailsManager jdbcUserDetailsManager(
            DataSource dataSource) {

        return new JdbcUserDetailsManager(dataSource);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {

        http.authorizeHttpRequests(auth -> auth

                // Pagine pubbliche
                .requestMatchers(
                        "/",
                        "/index",
                        "/signup",
                        "/signup-success",
                        "/login",
                        "/logout",
                        "/contatti",
                        "/css/**",
                        "/js/**",
                        "/img/**",
                        "/h2-console/**"
                ).permitAll()

                // Pagine private divise per ruolo
                .requestMatchers("/admin/**")
                .hasRole("ADMIN")

                .requestMatchers("/prova/**")
                .hasRole("USER_PROVA")

                .requestMatchers("/basic/**")
                .hasRole("USER_BASIC")

                .requestMatchers("/pro/**")
                .hasRole("USER_PRO")

                .anyRequest()
                .authenticated()
        );

        http.formLogin(form -> form
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/dashboard", true)
                .failureUrl("/login?error=true")
                .permitAll()
        );

        http.logout(logout -> logout
                .logoutUrl("/perform-logout")
                .logoutSuccessUrl("/logout")
                .permitAll()
        );

        http.csrf(csrf -> csrf
                .ignoringRequestMatchers("/h2-console/**")
        );

        http.headers(headers -> headers
                .frameOptions(frame -> frame.sameOrigin())
        );

        return http.build();
    }
}