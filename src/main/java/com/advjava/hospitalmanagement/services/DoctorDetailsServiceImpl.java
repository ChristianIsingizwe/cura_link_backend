package com.advjava.hospitalmanagement.services;

import com.advjava.hospitalmanagement.repositories.DoctorRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@AllArgsConstructor
public class DoctorDetailsServiceImpl implements UserDetailsService {

    private final DoctorRepository doctorRepository;

    @Override
    public UserDetails loadUserByUsername(String email){
        var doctor = doctorRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        System.out.println(doctor.getFullName());
        return new User(
                doctor.getEmail(),
                doctor.getPassword(),
                Collections.emptyList()
        );
    }
}
