package com.advjava.hospitalmanagement.config;

import com.advjava.hospitalmanagement.entities.AdminUserProperties;
import com.advjava.hospitalmanagement.entities.User;
import com.advjava.hospitalmanagement.entities.UserRole;
import com.advjava.hospitalmanagement.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AdminUserInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AdminUserProperties adminUserProperties;

    @Autowired
    public AdminUserInitializer(UserRepository userRepository,
                                PasswordEncoder passwordEncoder,
                                AdminUserProperties adminUserProperties) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.adminUserProperties = adminUserProperties;
    }

    @Override
    public void run(String... args) throws Exception {
        Optional<User> adminUser = userRepository.findByEmail(adminUserProperties.getEmail());
        if (adminUser.isEmpty()) {
            var user = new User();
            user.setFullName(adminUserProperties.getFullName());
            user.setEmail(adminUserProperties.getEmail());
            user.setPassword(passwordEncoder.encode(adminUserProperties.getPassword()));
            user.setRole(UserRole.ADMIN);
            userRepository.save(user);
            System.out.println("Admin user created");
        }
        else {
            System.out.println("Admin user already exists");
        }
    }
}
