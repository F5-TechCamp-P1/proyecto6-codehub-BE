package dev.proyect6.codehub.codehub.services;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import dev.proyect6.codehub.codehub.models.User;
import dev.proyect6.codehub.codehub.repositories.AuthRepository;

@Service

public class AuthService {

    private AuthRepository authRepository;
    @Value("${AUTH_APIKEY}")
    private String apikey;

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
        
        Optional<User> userValidated = users.stream()
                    .filter(user -> user.getUsername().equals(credentials.getUsername()) &&
                                    user.getPassword().equals(credentials.getPassword()))
                    .findFirst();

        userValidated.ifPresent(user -> {
            user.setApikey(apikey);
            authRepository.save(user);
        });


        return userValidated;

    }

    public Boolean filterAuth(String apiKey) {
        List<User> users = this.getAllUsers();
        System.out.println(users);
        return users.stream()
            //.filter(user -> user.getUsername().equals(apiKey))
            .filter(user -> {
                System.out.println("Verificando usuario: " + user.getUsername() + ", API Key: " + user.getApiKey());
                return Objects.equals(user.getApiKey(), apiKey);
            })
            .findFirst()
            .isPresent();
    }

    
}
