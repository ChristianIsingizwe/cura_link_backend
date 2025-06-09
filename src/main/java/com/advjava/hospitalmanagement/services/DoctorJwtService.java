package com.advjava.hospitalmanagement.services;

import com.advjava.hospitalmanagement.config.JwtConfig;
import com.advjava.hospitalmanagement.entities.Doctor;
import io.jsonwebtoken.Jwts;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@AllArgsConstructor
public class DoctorJwtService {
    private final JwtConfig jwtConfig;

    public Jwt generateDoctorAccessToken(Doctor user) {
        return generateDoctorToken(user, jwtConfig.getAccessTokenExpiration());
    }

    private Jwt generateDoctorToken(Doctor user, long tokenExpiration) {

        var claims = Jwts.claims()
                .subject(user.getId().toString())
                .add("name", user.getFullName())
                .add("role", user.getRole())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * tokenExpiration))
                .build();

        return new Jwt(claims, jwtConfig.getSecretKey());
    }

    public Jwt generateDoctorRefreshToken(Doctor user) {
        return generateDoctorToken(user, jwtConfig.getRefreshTokenExpiration());
    }
}
