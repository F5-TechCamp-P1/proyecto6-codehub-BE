package dev.proyect6.codehub.codehub.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.proyect6.codehub.codehub.models.User;

@Repository
public interface AuthRepository extends JpaRepository<User, Long> {
}
