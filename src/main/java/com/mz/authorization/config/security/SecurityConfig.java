package com.mz.authorization.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securitygWebFilterChain(ServerHttpSecurity http) {
        return http
                .authorizeExchange()
                    .pathMatchers("/public/**").permitAll()
                    .pathMatchers(HttpMethod.OPTIONS).permitAll()
                    .anyExchange().authenticated()
                .and()
                    .httpBasic()
                .and()
                    .formLogin().disable()
                .csrf()
                    .disable()
                .build();
    }

    @Bean
    public MapReactiveUserDetailsService userDetailsService() {
        UserDetails admin = User
                .withUsername("admin")
                .password(passwordEncoder().encode("password"))
                .roles("ADMIN")
                .build();

        return new MapReactiveUserDetailsService(admin);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
