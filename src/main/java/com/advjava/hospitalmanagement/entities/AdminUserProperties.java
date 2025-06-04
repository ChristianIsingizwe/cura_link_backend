package com.advjava.hospitalmanagement.entities;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "admin")
public class AdminUserProperties {
    private String fullName;
    private String email;
    private String password;
}
