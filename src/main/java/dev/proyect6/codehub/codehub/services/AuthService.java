package dev.proyect6.codehub.codehub.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import dev.proyect6.codehub.codehub.models.User;
import dev.proyect6.codehub.codehub.repositories.AuthRepository;

@Service
public class AuthService {

    private AuthRepository authRepository;
    public AuthService(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    public List<User> getAllUsers() {
        return authRepository.findAll();
    }
    public Optional<User> authenticate(User credentials) {
        List<User> users = this.getAllUsers();

        if (users == null || users.isEmpty()) {
            return Optional.empty();
        }
        return users.stream()
            .filter(user -> user.getUsername().equals(credentials.getUsername()) &&
                            user.getPassword().equals(credentials.getPassword()))
            .findFirst();

    }

    
}
