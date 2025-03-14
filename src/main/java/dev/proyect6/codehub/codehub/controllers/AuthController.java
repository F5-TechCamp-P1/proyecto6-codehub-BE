package dev.proyect6.codehub.codehub.controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.proyect6.codehub.codehub.models.User;
import dev.proyect6.codehub.codehub.services.AuthService;

@RestController
@RequestMapping("${api-endpoint}")
public class AuthController {
    
    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User credentials) {
        Optional<User> user = authService.authenticate(credentials);
        if (user.isPresent()) {
            Map<String, String> response = Map.of("apiKey", user.get().getApikey());
            return ResponseEntity.ok(response);

        } else {
            return ResponseEntity.status(401).body("Credenciales incorrectas");
        }
    }
    
}