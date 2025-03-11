package dev.proyect6.codehub.codehub.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class AuthController {

    @GetMapping("/login")
    public String login() {
        return "hola";
    }
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody String user) {
        return ResponseEntity.ok("Autenticación exitosa");
    }
}

