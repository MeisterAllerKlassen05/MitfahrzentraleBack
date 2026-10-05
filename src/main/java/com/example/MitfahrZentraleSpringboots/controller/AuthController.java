package com.example.MitfahrZentraleSpringboots.controller;

import com.example.MitfahrZentraleSpringboots.auth.JwtTokenProvider;
import com.example.MitfahrZentraleSpringboots.auth.dto.JwtResponse;
import com.example.MitfahrZentraleSpringboots.auth.dto.LoginRequest;
import com.example.MitfahrZentraleSpringboots.model.entity.User;
import com.example.MitfahrZentraleSpringboots.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;


    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Username bereits vergeben!"));
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole() != null ? request.getRole() : "USER");

        userRepository.save(user);
        return ResponseEntity.ok(Map.of("message", "User erfolgreich registriert!"));
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
        } catch (Exception e) {
            return ResponseEntity.status(401).body(Map.of("error", "Ungültiger Benutzername oder Passwort!"));
        }


        User user = userRepository.findByUsername(request.getUsername()).orElseThrow();
        String token = tokenProvider.generateToken(user.getUsername(), user.getRole());

        return ResponseEntity.ok(new JwtResponse(token, user.getUsername(), user.getRole()));
    }

    public static class RegisterRequest {
        private String username;
        private String password;
        private String role;

        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
        public String getRole() { return role; }
        public void setRole(String role) { this.role = role; }
    }
}
