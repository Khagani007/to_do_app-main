package com.khagani.to_do_app.configration;


import com.khagani.to_do_app.componenet.AuthenticationFilter;
import com.khagani.to_do_app.componenet.AuthorizationFilter;
import com.khagani.to_do_app.componenet.LimitLoginAuthenticationProvider;
import com.khagani.to_do_app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.util.Collections;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final UserDetailsService userDetailsService;

    @Value("${app.jwt.secret}")
    private String secret;

    private final AuthenticationEntryPoint entryPoint;
    private final  UserRepository userRepository;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        return new LimitLoginAuthenticationProvider(passwordEncoder(), userDetailsService);
    }

    @Bean
    public AuthenticationManager authenticationManagerBean() {
        return new ProviderManager(Collections.singletonList(authenticationProvider()));
    }

    @Bean
    public AuthenticationFilter authenticationFilter() {
        return new AuthenticationFilter(authenticationManagerBean(), secret);
    }

    @Bean
    public AuthorizationFilter authorizationFilter() {

        return new AuthorizationFilter(authenticationManagerBean(), userRepository);
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests(a -> a
                        .requestMatchers("/registration/**").permitAll()
                        .requestMatchers("/swagger-ui.html").permitAll()
                        .requestMatchers("/swagger-ui/**").permitAll()
                        .requestMatchers("/webjars**").permitAll()
                        .requestMatchers("/v3/api-docs/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/users").hasAuthority("CREATE_USER")
                        .requestMatchers(HttpMethod.GET,  "/users/**").hasAuthority("READ_USER")
                        .anyRequest().authenticated())
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterAt(authenticationFilter(), BasicAuthenticationFilter.class)
                .addFilterAfter(authorizationFilter(), AuthenticationFilter.class)
                .userDetailsService(userDetailsService)
                .sessionManagement(configurer -> configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(configurer->configurer.authenticationEntryPoint(entryPoint))
                .build();
    }

}
