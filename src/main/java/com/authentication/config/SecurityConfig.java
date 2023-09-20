package com.authentication.config;

import com.authentication.config.jwt.JwtFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author Artur Tomeyan
 * @date 02/11/2022
 */
@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Lazy
    private final JwtFilter jwtFilter;

    @Lazy
    private final AuthenticationProvider authenticationProvider;

    public SecurityConfig(JwtFilter jwtFilter, AuthenticationProvider authenticationProvider) {
        this.jwtFilter = jwtFilter;
        this.authenticationProvider = authenticationProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                    .authorizeRequests()
                        .antMatchers(HttpMethod.POST, "/api/v1/auth/login").permitAll()
                        .antMatchers(HttpMethod.POST, "/api/v1/auth/register").permitAll()
                        .antMatchers(HttpMethod.POST, "/api/v1/auth/refresh-token").authenticated()
                        .antMatchers(HttpMethod.POST, "/api/v1/auth/logout").authenticated()
                .anyRequest()
                    .authenticated()
                .and()
                    .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .logout()
                    .logoutUrl("/api/v1/auth/logout")
                        .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext());

        return http.build();
    }
}