package dev.proyect6.codehub.codehub.config;

import org.springframework.stereotype.Component;

import dev.proyect6.codehub.codehub.services.AuthService;

@Component
public class AuthFilter {
    

    private final AuthService authService;

    public AuthFilter(AuthService authService) {
        this.authService = authService;
    }

    public boolean preHandle(String apiKey) {
        return apiKey != null && authService.filterAuth(apiKey);
    }
}
