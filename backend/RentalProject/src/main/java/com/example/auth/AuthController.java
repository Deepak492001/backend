package com.example.auth;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.example.jwt.JWTService;
import com.example.user.MyUserDetailsService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private MyUserDetailsService myUserDetailsService;
    @Autowired
    private JWTService jwtService;

    @PostMapping("/validate")
    public ResponseEntity<Map<String, String>> loginUser(@RequestParam("userEmail") String userEmail,
            @RequestParam("userPassword") String userPassword,
            HttpServletRequest request) {
        Map<String, String> responseMap = new HashMap<>();

        // Validate the user and generate the JWT token
        String jwtToken = authService.validateUser(userEmail, userPassword);
        System.out.println("Token" + jwtToken);
        // Add success message and token to the response map
        responseMap.put("message", "Successfully Logged in");
        responseMap.put("token", jwtToken);

        return new ResponseEntity<>(responseMap, HttpStatus.OK);

    }

    @GetMapping("/validate-token")
    public ResponseEntity<Map<String, String>> validateToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Invalid or missing Authorization header"));
        }

        try {
            String bearerToken = authorizationHeader.replace("Bearer ", "");

            UserDetails userDetails = myUserDetailsService.loadUserByUsername(jwtService.extractUserEmail(bearerToken));

            boolean isValid = jwtService.validateToken(bearerToken, userDetails);

            if (isValid) {
                return ResponseEntity.ok(Map.of(
                        "isValid", "true",
                        "message", "Token is valid and matched with the user."));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of(
                                "isValid", "false",
                                "message", "Token is invalid or does not match the user."));
            }
        } catch (Exception e) {
            // Handle any unexpected errors
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                            "error", "An error occurred while validating the token",
                            "details", e.getMessage()));
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<Map<String, String>> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
            return ResponseEntity.ok(Map.of("message","Logout successful"));
        } else {
            return new ResponseEntity<>(Map.of("message", "No active session found"), HttpStatus.NOT_FOUND);
        }
    }

}