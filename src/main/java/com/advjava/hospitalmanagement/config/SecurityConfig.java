package com.advjava.hospitalmanagement.config;

import com.advjava.hospitalmanagement.entities.UserRole;
import com.advjava.hospitalmanagement.filters.JwtAuthenticationFilter;
import com.advjava.hospitalmanagement.services.DoctorDetailsServiceImpl;
import com.advjava.hospitalmanagement.services.UserDetailsServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {

    private UserDetailsServiceImpl userDetailsService;
    private DoctorDetailsServiceImpl doctorDetailsService;
    private  JwtAuthenticationFilter jwtAuthenticationFilter;


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager manualAuthenticationManager(){
        DaoAuthenticationProvider doctorProvider = new DaoAuthenticationProvider(doctorDetailsService);
        doctorProvider.setPasswordEncoder(passwordEncoder());

        DaoAuthenticationProvider userProvider = new DaoAuthenticationProvider(userDetailsService);
        userProvider.setPasswordEncoder(passwordEncoder());

        return new ProviderManager(List.of(doctorProvider, userProvider));
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, CorsConfigurationSource corsConfigurationSource) throws Exception {
        http
                .cors( c -> c.configurationSource(corsConfigurationSource))
                .sessionManagement(c -> c.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(c -> c
                        .requestMatchers("/auth/login").permitAll()
                        .requestMatchers("/patient/register").permitAll()
                        .requestMatchers("/doctors/login").permitAll()
                        .requestMatchers("/auth/refresh").permitAll()
                        .requestMatchers(HttpMethod.POST,"/appointments").hasRole(UserRole.PATIENT.name())
                        .requestMatchers("/appointments/update/approve/{id}").hasRole(UserRole.DOCTOR.name())
                        .requestMatchers("/appointments/doctors/{id}").hasRole(UserRole.DOCTOR.name())
                        .requestMatchers(HttpMethod.POST, "/doctors/add").hasRole(UserRole.ADMIN.name())
                        .requestMatchers(HttpMethod.DELETE, "/doctors/{id}").hasRole(UserRole.ADMIN.name())
                        .requestMatchers(HttpMethod.DELETE, "/patient/delete/{id}").hasRole(UserRole.ADMIN.name())
                        .requestMatchers(HttpMethod.GET, "/patient/all").hasRole(UserRole.ADMIN.name())
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .anyRequest().authenticated()
                )
                .authenticationProvider(doctorAuthenticationProvider())
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(c ->{
                    c.authenticationEntryPoint(
                            new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED));
                    c.accessDeniedHandler((request, response, accessDeniedException) -> {
                        response.setStatus(HttpStatus.FORBIDDEN.value());
                    });
                });
        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        var provider = new DaoAuthenticationProvider(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationProvider doctorAuthenticationProvider() {
        var provider = new DaoAuthenticationProvider(doctorDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

}
