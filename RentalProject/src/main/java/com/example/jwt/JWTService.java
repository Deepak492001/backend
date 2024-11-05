package com.example.jwt;

import org.springframework.security.core.userdetails.UserDetails;

public interface JWTService {

    String generateToken(String userEmail);

    String extractUserEmail(String token);

    boolean validateToken(String token, UserDetails userDetails);

}
