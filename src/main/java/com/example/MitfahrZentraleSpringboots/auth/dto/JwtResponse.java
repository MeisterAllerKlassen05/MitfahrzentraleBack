package com.example.MitfahrZentraleSpringboots.auth.dto;

public class JwtResponse {
    private String token;
    private String username;
    private String role;
    private boolean activ;

    public JwtResponse(String token, String username, String role, boolean activ) {
        this.token = token;
        this.username = username;
        this.role = role;
        this.activ = activ;
    }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public boolean isActiv() { return activ; }
}
